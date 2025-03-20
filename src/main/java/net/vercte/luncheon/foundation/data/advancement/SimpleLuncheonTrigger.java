package net.vercte.luncheon.foundation.data.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

public class SimpleLuncheonTrigger extends LuncheonCriterionTriggerBase<SimpleLuncheonTrigger.Instance> {

    public SimpleLuncheonTrigger(String id) {
        super(id);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull Instance createInstance(JsonObject json, DeserializationContext context) {
        return new Instance(getId());
    }

    public void trigger(ServerPlayer player) {
        super.trigger(player, null);
    }

    public Instance instance() {
        return new Instance(getId());
    }

    public static class Instance extends LuncheonCriterionTriggerBase.Instance {

        public Instance(ResourceLocation idIn) {
            super(idIn, ContextAwarePredicate.ANY);
        }

        @Override
        protected boolean test(@Nullable List<Supplier<Object>> suppliers) {
            return true;
        }
    }
}
