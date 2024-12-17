package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.vercte.luncheon.Luncheon;

public class CoolerBlockEntity extends KineticBlockEntity {
    private int drainSpeed = 40;
    private int satisfiedTicks;

    private boolean active = false;
    private CoolerFluidTank tankInventory;
    protected LazyOptional<IFluidHandler> fluidCapability;

    public CoolerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        satisfiedTicks = 0;

        tankInventory = new CoolerFluidTank();
        fluidCapability = LazyOptional.of(() -> tankInventory);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        satisfiedTicks = compound.getInt("satisfiedTicks");
        active = compound.getBoolean("active");
        tankInventory.readFromNBT(compound);

        super.read(compound, clientPacket);
    }

    @Override
    protected void write(CompoundTag compoundTag, boolean clientPacket) {
        compoundTag.putInt("satisfiedTicks", satisfiedTicks);
        compoundTag.putBoolean("active", active);
        tankInventory.writeToNBT(compoundTag);

        super.write(compoundTag, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();

        if(level.isClientSide) return;

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

    protected void setBlockState() {
        setBlockCooling(getCoolingLevel());
    }

    protected void setBlockCooling(CoolerBlock.CoolingLevel coolingLevel) {
        CoolerBlock.CoolingLevel inBlockState = getCoolingLevelFromBlock();
        if (inBlockState == coolingLevel)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(CoolerBlock.COOL_LEVEL, coolingLevel));
        notifyUpdate();
    }

    protected CoolerBlock.CoolingLevel getCoolingLevel() {
        if(active) return CoolerBlock.CoolingLevel.COOLED;
        return CoolerBlock.CoolingLevel.NONE;
    }

    public CoolerBlock.CoolingLevel getCoolingLevelFromBlock() {
        return CoolerBlock.getCoolingLevelOf(getBlockState());
    }

    public float getBladeRotationSpeed() {
        if(!active) return Math.max(-16, Math.min(speed / 2, 16));
        return speed;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isFluidHandlerCap(cap)
                && (side == null || CoolerBlock.hasPipeTowards(level, worldPosition, getBlockState(), side)))
            return this.fluidCapability.cast();
        return super.getCapability(cap, side);
    }
}
