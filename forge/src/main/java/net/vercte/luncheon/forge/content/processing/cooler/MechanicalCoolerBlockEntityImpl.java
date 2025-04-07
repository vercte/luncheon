package net.vercte.luncheon.forge.content.processing.cooler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlock;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlockEntity;
import org.jetbrains.annotations.NotNull;

abstract public class MechanicalCoolerBlockEntityImpl extends MechanicalCoolerBlockEntity {
    public MechanicalCoolerBlockEntityImpl(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) { super(typeIn, pos, state); }

    protected LazyOptional<IFluidHandler> fluidCapability;
    public void initCapabilities() {
        fluidCapability = LazyOptional.of(() -> tankInventory);
    }

    @SuppressWarnings("DataFlowIssue")
    public void tick() {
        super.tick();

        if (fluidLevel != null) fluidLevel.tickChaser();

        if (syncCooldown > 0) {
            syncCooldown--;
            if (syncCooldown == 0 && queuedSync)
                sendData();
        }

        if(level.isClientSide) {
            spawnParticles();
            if(!isVirtual()) return;
        }

        active = false;
        if(isSpeedRequirementFulfilled()) {
            if(satisfiedTicks > 0) satisfiedTicks--;
            if(satisfiedTicks == 0) {
                FluidStack drained = tankInventory.drain(drainSpeed, IFluidHandler.FluidAction.SIMULATE);
                if(drained.getAmount() == drainSpeed) {
                    tankInventory.drain(drainSpeed, IFluidHandler.FluidAction.EXECUTE);
                    satisfiedTicks = 5;
                }
            }

            active = satisfiedTicks > 0;
        }

        setBlockState();
    }

    @NotNull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isFluidHandlerCap(cap)
                && (side == null || MechanicalCoolerBlock.hasPipeTowards(side)))
            return this.fluidCapability.cast();
        return super.getCapability(cap, side);
    }
}
