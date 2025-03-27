package net.vercte.luncheon.content.misc;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.vercte.luncheon.foundation.data.advancement.AutomationTrigger;

import java.util.LinkedList;
import java.util.List;

public class LuncheonCriteriaTriggers {
    private static final List<SimpleCriterionTrigger<?>> triggers = new LinkedList<>();


    private static <T extends SimpleCriterionTrigger<?>> T add(T instance) {
        triggers.add(instance);
        return instance;
    }

    public static final AutomationTrigger AUTOMATION = add(new AutomationTrigger());

    public static void register() { triggers.forEach(CriteriaTriggers::register); }
}
