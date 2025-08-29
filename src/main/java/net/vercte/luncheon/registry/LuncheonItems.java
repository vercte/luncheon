package net.vercte.luncheon.registry;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.item.*;
import net.vercte.luncheon.registry.custom.LuncheonRegistrate;

@SuppressWarnings("SameParameterValue")
public class LuncheonItems {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    static {
        REGISTRATE.setCreativeTab(Luncheon.BASE_CREATIVE_TAB);
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }

    // region Foods
    public static final ItemEntry<BaguetteItem> BAGUETTE = REGISTRATE.item("baguette", BaguetteItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BAGUETTE))
            .model(AssetLookup.existingItemModel())
            .lang("Baguette")
            .register();

    public static final ItemEntry<Item> BREAD_SLICE = REGISTRATE.item("bread_slice", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.BREAD_SLICE))
            .lang("Bread Slice")
            .register();

    public static final ItemEntry<Item> PLAIN_ICE_CREAM = REGISTRATE.item("plain_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.PLAIN_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag, LuncheonTags.ItemTags.ICE_CREAM_CONES.tag)
            .lang("Plain Ice Cream")
            .register();

    public static final ItemEntry<Item> CHOCOLATE_ICE_CREAM = REGISTRATE.item("chocolate_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.CHOCOLATE_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag, LuncheonTags.ItemTags.ICE_CREAM_CONES.tag)
            .lang("Chocolate Ice Cream")
            .register();

    public static final ItemEntry<Item> BERRY_ICE_CREAM = REGISTRATE.item("berry_ice_cream", Item::new)
            .properties(p -> p.food(LuncheonFoodProperties.BERRY_ICE_CREAM))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag, LuncheonTags.ItemTags.ICE_CREAM_CONES.tag)
            .lang("Berry Ice Cream")
            .register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_NEAPOLITAN_SUNDAE = REGISTRATE.item("incomplete_neapolitan_sundae", SequencedAssemblyItem::new)
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .model(AssetLookup.existingItemModel())
            .lang("Incomplete Neapolitan Sundae")
            .register();

    public static final ItemEntry<NeapolitanSundaeItem> NEAPOLITAN_SUNDAE = REGISTRATE.item("neapolitan_sundae", NeapolitanSundaeItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.NEAPOLITAN_SUNDAE).stacksTo(16))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .lang("Neapolitan Sundae")
            .register();

    public static final ItemEntry<BottleFoodItem> BERRY_EXTRACT = REGISTRATE.item("berry_extract", BottleFoodItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BERRY_EXTRACT).stacksTo(16))
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .lang("Bottle of Berry Extract")
            .register();
    // endregion

    // region Misc
    public static final ItemEntry<Item> RAW_WAFER = ingredient("raw_wafer", "Raw Wafer");
    public static final ItemEntry<Item> WAFER = ingredient("wafer", "Wafer");
    public static final ItemEntry<Item> ICE_CREAM_CONE = taggedIngredient("ice_cream_cone", "Ice Cream Cone", AllItemTags.UPRIGHT_ON_BELT.tag);
    public static final ItemEntry<Item> BAGUETTE_DOUGH = ingredient("baguette_dough", "Baguette Dough");
    public static final ItemEntry<Item> BUTTER = ingredient("butter", "Butter");

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
