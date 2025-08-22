package net.vercte.luncheon.content.mechanical_cooler;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;
import org.jetbrains.annotations.NotNull;

public class MechanicalCoolerBlockEntity extends KineticBlockEntity {
    private boolean active;

    private static final int SYNC_RATE = 8;
    private int syncCooldown;
    private boolean queuedSync;

    private final MechanicalCoolerFluidTank tank;
    protected LazyOptional<IFluidHandler> fluidCapability;

    private LerpedFloat fluidLevel;

    public MechanicalCoolerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.active = state.getValue(MechanicalCoolerBlock.ACTIVE);
        this.tank = new MechanicalCoolerFluidTank(this::tankUpdate);
        this.fluidCapability = LazyOptional.of(() -> tank);
    }

    @Override
    public void sendData() {
        if (syncCooldown > 0) {
            queuedSync = true;
            return;
        }
        super.sendData();
        queuedSync = false;
        syncCooldown = SYNC_RATE;
    }

    @Override
    public void tick() {
        super.tick();

        if (fluidLevel != null) fluidLevel.tickChaser();

        if (syncCooldown > 0) {
            syncCooldown--;
            if (syncCooldown == 0 && queuedSync)
                sendData();
        }

        assert level != null;
        if(level.isClientSide && !isVirtual()) return;

        if(!isSpeedRequirementFulfilled() || this.tank.getFluidAmount() < 50) {
            active = false;
            return;
        }

        if(this.tank.getFluidAmount() >= 50) {
            active = true;
            this.tank.drain(50, EXECUTE);
        }

        updateActive();
    }

    public float getFanRotationSpeed() {
        if(!isActive()) return Mth.clamp(speed / 2, -8, 8);
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void updateActive() {
        boolean isActive = getBlockState().getValue(MechanicalCoolerBlock.ACTIVE);
        if (isActive == active)
            return;
        assert level != null;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(MechanicalCoolerBlock.ACTIVE, active));
        notifyUpdate();
    }

    public void tankUpdate(FluidStack stack) {
        if(!hasLevel())
            return;

        assert level != null;
        if(!level.isClientSide) {
            setChanged();
            sendData();
        }

        if (isVirtual()) {
            if (fluidLevel == null) fluidLevel = LerpedFloat.linear().startWithValue(getFillState());
            fluidLevel.chase(getFillState(), 0.5f, LerpedFloat.Chaser.EXP);
        }
    }

    public MechanicalCoolerFluidTank getTank() {
        return this.tank;
    }

    public LerpedFloat getFluidLevel() {
        return this.fluidLevel;
    }

    public float getFillState() {
        return (float) tank.getFluidAmount() / tank.getCapacity();
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        assert level != null;
        if (isFluidHandlerCap(cap) && (side == null || side.getAxis() != Direction.Axis.Y))
            return this.fluidCapability.cast();
        return super.getCapability(cap, side);
    }
}
