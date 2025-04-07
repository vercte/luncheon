package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.world.level.material.Fluids;

public class MechanicalCoolerFluidTank extends SmartFluidTank {
    public MechanicalCoolerFluidTank(Runnable callback) {
        super(800, s -> callback.run());
        setValidator(f -> f.getFluid().isSame(Fluids.WATER));
    }
}
