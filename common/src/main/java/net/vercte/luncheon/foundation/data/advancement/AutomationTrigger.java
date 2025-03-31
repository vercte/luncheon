package net.vercte.luncheon.foundation.data.advancement;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.vercte.luncheon.Luncheon;

public class AutomationTrigger extends SimpleCriterionTrigger<AutomationTrigger.TriggerInstance> {
    private static final ResourceLocation ID = Luncheon.asResource("automation");

    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate ctx, DeserializationContext deserialization) {
        return new TriggerInstance(ctx, ItemPredicate.fromJson(json.get("item")));
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.matches(stack));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate ctx, ItemPredicate item) {
            super(AutomationTrigger.ID, ctx);
            this.item = item;
        }

        public static TriggerInstance automated() {
            return new TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.ANY);
        }

        public static TriggerInstance automated(ItemPredicate itemPredicate) {
            return new TriggerInstance(ContextAwarePredicate.ANY, itemPredicate);
        }

        public static TriggerInstance automated(ItemLike item) {
            return new TriggerInstance(ContextAwarePredicate.ANY, new ItemPredicate(null, ImmutableSet.of(item.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
        }

        public boolean matches(ItemStack p_23702_) {
            return this.item.matches(p_23702_);
        }

        public JsonObject serializeToJson(SerializationContext p_23706_) {
            JsonObject jsonobject = super.serializeToJson(p_23706_);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}
