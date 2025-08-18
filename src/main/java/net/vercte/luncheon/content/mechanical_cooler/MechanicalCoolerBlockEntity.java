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
import org.jetbrains.annotations.NotNull;

public class MechanicalCoolerBlockEntity extends KineticBlockEntity {
    private CoolingCondition state;

    private final MechanicalCoolerFluidTank tank;
    protected LazyOptional<IFluidHandler> fluidCapability;

    private LerpedFloat fluidLevel;

    public MechanicalCoolerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.state = state.getValue(MechanicalCoolerBlock.COOLING);
        this.tank = new MechanicalCoolerFluidTank(this::tankUpdate);
        this.fluidCapability = LazyOptional.of(() -> tank);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public float getFanRotationSpeed() {
        if(this.state == CoolingCondition.NONE) return Mth.clamp(speed / 2, -8, 8);
        return speed;
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
