package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class CoolerFluidTank extends SmartFluidTank {
    public CoolerFluidTank(Consumer<FluidStack> callback) {
        super(800, callback);
        setValidator(f -> f.getFluid().isSame(Fluids.WATER));
    }
}
