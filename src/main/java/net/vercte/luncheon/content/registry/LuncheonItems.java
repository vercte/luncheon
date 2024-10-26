package net.vercte.luncheon.content.registry;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.item.BaguetteItem;
import net.vercte.luncheon.content.item.LuncheonFoodProperties;

public class LuncheonItems {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final ItemEntry<Item> GLASS_JAR = REGISTRATE.item("glass_jar", Item::new).register();

    // region Foods
    public static final ItemEntry<BaguetteItem> BAGUETTE = REGISTRATE.item("baguette", BaguetteItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BAGUETTE))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.luncheon.baguette"))
            .lang("Baguette")
            .register();

    public static final ItemEntry<CombustibleItem> BLAZE_CAKE_SLICE = REGISTRATE.item("blaze_cake_slice", CombustibleItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BLAZE_CAKE_SLICE))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .onRegister(i -> i.setBurnTime(1600))
            .lang("Slice of Blaze Cake")
            .register();

    public static final ItemEntry<Item> ICE_CUBE = REGISTRATE.item("ice_cube", Item::new)
            .lang("Ice Cream").register();
    // endregion

    // region Crops
    public static final ItemEntry<Item> FIREBERRY = REGISTRATE.item("fireberry", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.FIREBERRY))
            .lang("Fireberry")
            .register();

    public static final ItemEntry<ItemNameBlockItem> FIREBERRY_SEEDS = REGISTRATE.item("fireberry_seeds", p -> new ItemNameBlockItem(LuncheonBlocks.BUDDING_FIREBERRY_CROP.get(), p))
            .lang("Fireberry Seeds")
            .register();
    // endregion

    public static void register() {}
}
