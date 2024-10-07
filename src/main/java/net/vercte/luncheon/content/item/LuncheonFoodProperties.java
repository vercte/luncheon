package net.vercte.luncheon.content.item;

import net.minecraft.world.food.FoodProperties;

public class LuncheonFoodProperties {
    public static final FoodProperties BAGUETTE = food(6, 0.7F, false);

    public static FoodProperties food(int nutrition, float saturationMod, boolean canAlwaysEat) {
        FoodProperties.Builder foodProperties = new FoodProperties.Builder();
        foodProperties.nutrition(nutrition);
        foodProperties.saturationMod(saturationMod);
        if(canAlwaysEat) {
            foodProperties.alwaysEat();
        }
        return foodProperties.build();
    }
}
