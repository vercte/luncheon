package net.vercte.luncheon.content.registry;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.item.BaguetteItem;
import net.vercte.luncheon.content.item.GlassShardsItem;
import net.vercte.luncheon.content.item.LuncheonFoodProperties;
import net.vercte.luncheon.content.item.NotActuallyAFoodItem;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonItems {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    static {
        REGISTRATE.setCreativeTab(Luncheon.BASE_CREATIVE_TAB);
    }

    // region Foods
    public static final ItemEntry<BaguetteItem> BAGUETTE = REGISTRATE.item("baguette", BaguetteItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BAGUETTE))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.luncheon.baguette"))
            .model(AssetLookup.existingItemModel())
            .lang("Baguette")
            .register();

    public static final ItemEntry<Item> PLAIN_ICE_CREAM = REGISTRATE.item("plain_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.PLAIN_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .lang("Plain Ice Cream")
            .register();

    public static final ItemEntry<Item> CHOCOLATE_ICE_CREAM = REGISTRATE.item("chocolate_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.CHOCOLATE_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .lang("Chocolate Ice Cream")
            .register();

    public static final ItemEntry<Item> BERRY_ICE_CREAM = REGISTRATE.item("berry_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.BERRY_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .lang("Berry Ice Cream")
            .register();
    // endregion

    // region Misc
    public static final ItemEntry<Item> RAW_WAFER = ingredient("raw_wafer", "Raw Wafer");
    public static final ItemEntry<Item> WAFER = ingredient("wafer", "Wafer");
    public static final ItemEntry<Item> ICE_CREAM_CONE = taggedIngredient("ice_cream_cone", "Ice Cream Cone", AllItemTags.UPRIGHT_ON_BELT.tag);

    public static final ItemEntry<NotActuallyAFoodItem> ICE_CUBE = REGISTRATE.item("ice_cube", NotActuallyAFoodItem::new)
            .lang("Ice Cube").register();

    public static final ItemEntry<GlassShardsItem> GLASS_SHARDS = REGISTRATE.item("glass_shards", GlassShardsItem::new)
            .lang("Glass Shards").register();
    // endregion

    private static ItemEntry<Item> ingredient(String name, String lang) {
        return REGISTRATE.item(name, Item::new)
                .lang(lang)
                .register();
    }

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, String lang, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .lang(lang)
                .tag(tags)
                .register();
    }

    public static void register() {}
}
