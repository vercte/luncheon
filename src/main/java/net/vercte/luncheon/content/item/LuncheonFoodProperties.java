package net.vercte.luncheon.content.item;

import net.minecraft.world.food.FoodProperties;

public class LuncheonFoodProperties {
    public static final FoodProperties BAGUETTE = food(6, 0.7F).build();
    public static final FoodProperties BLAZE_CAKE_SLICE = food(2, 0.1F, true).build();

    public static final FoodProperties FIREBERRY = food(2, 0.1F).build();

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
