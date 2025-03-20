package net.vercte.luncheon.foundation.data.advancement;

import net.minecraft.advancements.CriteriaTriggers;

import java.util.LinkedList;
import java.util.List;

public class LuncheonTriggers {

    private static final List<LuncheonCriterionTriggerBase<?>> triggers = new LinkedList<>();

    public static SimpleLuncheonTrigger addSimple(String id) {
        return add(new SimpleLuncheonTrigger(id));
    }

    private static <T extends LuncheonCriterionTriggerBase<?>> T add(T instance) {
        triggers.add(instance);
        return instance;
    }

    public static void register() {
        triggers.forEach(CriteriaTriggers::register);
    }

}