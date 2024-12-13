package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.List;

public class CoolerBlockEntity extends KineticBlockEntity {
    private SmartFluidTank waterTank;
    private LazyOptional<IFluidHandler> capability;

    public CoolerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        waterTank = new SmartFluidTank(1500, this::onTankContentsChanged);
    }

    protected void onTankContentsChanged(FluidStack fluid) {}

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isFluidHandlerCap(cap)
                && (side == null || HosePulleyBlock.hasPipeTowards(level, worldPosition, getBlockState(), side)))
            return this.capability.cast();
        return super.getCapability(cap, side);
    }
}
