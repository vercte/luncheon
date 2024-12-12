package net.vercte.luncheon.content.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.vercte.luncheon.content.registry.LuncheonMobEffects;

public class LuncheonFoodProperties {
    public static final FoodProperties BAGUETTE = food(5, 0.6F).build();

    public static final FoodProperties BLAZE_CAKE_SLICE = food(2, 0.1F, true)
            .effect(() -> new MobEffectInstance(LuncheonMobEffects.SPICY.get(), 8 * 20), 1).build();

    public static final FoodProperties CHILI = food(2, 0.1F)
            .effect(() -> new MobEffectInstance(LuncheonMobEffects.SPICY.get(), 15 * 20), 1).build();

    public static FoodProperties.Builder food(int nutrition, float saturationMod) {
        FoodProperties.Builder foodProperties = new FoodProperties.Builder();
        foodProperties.nutrition(nutrition);
        foodProperties.saturationMod(saturationMod);
        return foodProperties;
    }

    public static FoodProperties.Builder food(int nutrition, float saturationMod, boolean fast) {
        FoodProperties.Builder foodProperties = food(nutrition, saturationMod);
        if(fast) { foodProperties.fast(); }
        return foodProperties;
    }

    public static FoodProperties.Builder food(int nutrition, float saturationMod, boolean fast, boolean canAlwaysEat) {
        FoodProperties.Builder foodProperties = food(nutrition, saturationMod, fast);
        if(canAlwaysEat) { foodProperties.alwaysEat(); }
        return foodProperties;
    }
}
