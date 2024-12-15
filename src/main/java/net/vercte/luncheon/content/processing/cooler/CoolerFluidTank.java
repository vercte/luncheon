package net.vercte.luncheon.content.processing.cooler;

import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CoolerFluidTank extends FluidTank {
    public CoolerFluidTank() {
        super(1000, e -> e.getFluid().isSame(Fluids.WATER));
    }
}
