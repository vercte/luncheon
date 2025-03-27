package net.vercte.luncheon.content.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class LuncheonFoodProperties {
    public static final FoodProperties BAGUETTE = food(3, 0.6F).build();
    public static final FoodProperties BREAD_SLICE = food(2, 0.6F).fast().build();

    public static final FoodProperties PLAIN_ICE_CREAM = food(4,  0.7F).build();
    public static final FoodProperties CHOCOLATE_ICE_CREAM = food(6, 0.8F).build();
    public static final FoodProperties BERRY_ICE_CREAM = food(5, 0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300), 1).build();

    public static final FoodProperties NEAPOLITAN_SUNDAE = food(14, 0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 800), 1)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200), 1)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1).build();

    public static final FoodProperties BERRY_EXTRACT = food(1, 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100), 0.2f).build();

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
