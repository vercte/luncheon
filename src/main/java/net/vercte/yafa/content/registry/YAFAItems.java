package net.vercte.yafa.content.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.vercte.yafa.YAFA;
import net.vercte.yafa.content.item.BaguetteItem;

public class YAFAItems {
    private static final CreateRegistrate REGISTRATE = YAFA.registrate();

    public static final ItemEntry<BaguetteItem> BAGUETTE = REGISTRATE.item("baguette", BaguetteItem::new)
            .properties(p -> p.food(
                    makeFood(6, 0.7F, false)
            ))
            .register();

    public static final ItemEntry<Item> ICE_CUBE = REGISTRATE.item("ice_cube", Item::new)
            .properties(p -> p.food(
                    makeFood(0,0, true)
            ))
            .register();


    public static void register() {
    }

    public static FoodProperties makeFood(int nutrition, float saturationMod, boolean canAlwaysEat) {
         FoodProperties.Builder foodProperties = new FoodProperties.Builder();
                foodProperties.nutrition(nutrition);
                foodProperties.saturationMod(saturationMod);
                if(canAlwaysEat) {
                    foodProperties.alwaysEat();
                }
         return foodProperties.build();
    }
}
