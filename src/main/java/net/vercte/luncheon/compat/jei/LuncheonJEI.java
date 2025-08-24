package net.vercte.luncheon.compat.jei;

import com.simibubi.create.compat.jei.CreateJEI;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.compat.jei.category.animations.AnimatedMechanicalCooler;
import net.vercte.luncheon.registry.LuncheonHeatConditions;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class LuncheonJEI implements IModPlugin {
    private static final AnimatedMechanicalCooler COOLER = new AnimatedMechanicalCooler();

    public LuncheonJEI() {}

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        CreateJEI.heatConditionDrawables.put(LuncheonHeatConditions.COOLED, COOLER);
    }

    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return Luncheon.asResource("jei");
    }
}
