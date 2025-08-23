package net.vercte.luncheon.registry;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import net.minecraft.core.Registry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.CooledCondition;

public class LuncheonHeatConditions {
    public static final CooledCondition COOLED = Registry.register(CreateBuiltInRegistries.HEAT_CONDITION, Luncheon.asResource("cooled"), new CooledCondition());

    public static void register() {}
}
