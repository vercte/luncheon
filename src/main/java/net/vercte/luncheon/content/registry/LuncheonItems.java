package net.vercte.luncheon.content.registry;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.item.BaguetteItem;
import net.vercte.luncheon.content.item.GlassShardsItem;
import net.vercte.luncheon.content.item.LuncheonFoodProperties;
import net.vercte.luncheon.content.item.NotActuallyAFoodItem;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonItems {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

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

    public static final ItemEntry<NotActuallyAFoodItem> ICE_CUBE = REGISTRATE.item("ice_cube", NotActuallyAFoodItem::new)
            .lang("Ice Cream").register();

    public static final ItemEntry<GlassShardsItem> GLASS_SHARDS = REGISTRATE.item("glass_shards", GlassShardsItem::new)
            .lang("Glass Shards").register();
    // endregion

    // region Crops
    public static final ItemEntry<Item> CHILI = REGISTRATE.item("chili", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.CHILI))
            .lang("Chili")
            .register();

    public static final ItemEntry<ItemNameBlockItem> CHILI_SEEDS = REGISTRATE.item("chili_seeds", p -> new ItemNameBlockItem(LuncheonBlocks.BUDDING_CHILI_CROP.get(), p))
            .lang("Chili Seeds")
            .register();
    // endregion

    public static void register() {}
}
