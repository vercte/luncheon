package net.vercte.luncheon.content.registry.custom;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.RegistryObject;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonTags;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class LuncheonDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
    private static final Predicate<Item> IS_ITEM_3D_PREDICATE;
    private static final Predicate<Item> IS_BUILDING_BLOCK_PREDICATE;

    static {
        MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            isItem3d.setValue(item -> {
                ItemRenderer itemRenderer = Minecraft.getInstance()
                        .getItemRenderer();
                BakedModel model = itemRenderer.getModel(new ItemStack(item), null, null, 0);
                return model.isGui3d();
            });
        });
        IS_ITEM_3D_PREDICATE = isItem3d.getValue();
        IS_BUILDING_BLOCK_PREDICATE = (item -> item.getDefaultInstance().is(LuncheonTags.ItemTags.BUILDING_BLOCKS.tag));
    }

    private final RegistryObject<CreativeModeTab> tabFilter;

    public LuncheonDisplayItemsGenerator(RegistryObject<CreativeModeTab> tabFilter) {
        this.tabFilter = tabFilter;
    }

    private static Predicate<Item> makeExclusionPredicate() {
        Set<Item> exclusions = new ReferenceOpenHashSet<>();

        List<ItemProviderEntry<?>> simpleExclusions = List.of(
                LuncheonItems.INCOMPLETE_NEAPOLITAN_SUNDAE
        );

        for (ItemProviderEntry<?> entry : simpleExclusions) {
            exclusions.add(entry.asItem());
        }

        return exclusions::contains;
    }

    private static List<ItemOrdering> makeOrderings() {
        List<ItemOrdering> orderings = new ReferenceArrayList<>();

        Map<ItemProviderEntry<?>, ItemProviderEntry<?>> simpleBeforeOrderings = Map.of(
                LuncheonItems.BAGUETTE_DOUGH, LuncheonItems.BAGUETTE,
                LuncheonItems.RAW_WAFER, LuncheonItems.WAFER,
                LuncheonItems.WAFER, LuncheonItems.ICE_CREAM_CONE,
                LuncheonItems.ICE_CREAM_CONE, LuncheonItems.PLAIN_ICE_CREAM
        );

        Map<ItemProviderEntry<?>, ItemProviderEntry<?>> simpleAfterOrderings = Map.of(
                LuncheonItems.ICE_CUBE, LuncheonBlocks.MECHANICAL_COOLER,
                LuncheonBlocks.COBBLED_GLASS, LuncheonItems.GLASS_SHARDS
        );

        simpleBeforeOrderings.forEach((entry, otherEntry) -> {
            orderings.add(ItemOrdering.before(entry.asItem(), otherEntry.asItem()));
        });

        simpleAfterOrderings.forEach((entry, otherEntry) -> {
            orderings.add(ItemOrdering.after(entry.asItem(), otherEntry.asItem()));
        });

        return orderings;
    }

    private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Item> entry : Luncheon.registrate().getAll(Registries.ITEM)) {
            if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                continue;
            Item item = entry.get();
            if (item instanceof BlockItem)
                continue;
            if (!exclusionPredicate.test(item))
                items.add(item);
        }
        return items;
    }

    private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Block> entry : Luncheon.registrate().getAll(Registries.BLOCK)) {
            if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
                continue;
            Item item = entry.get()
                    .asItem();
            if (item == Items.AIR)
                continue;
            if (!exclusionPredicate.test(item))
                items.add(item);
        }
        items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
        return items;
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        Predicate<Item> exclusionPredicate = makeExclusionPredicate();
        List<ItemOrdering> orderings = makeOrderings();
        List<Item> items = new LinkedList<>();

        items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
        items.addAll(collectBlocks(exclusionPredicate.or(IS_BUILDING_BLOCK_PREDICATE)));
        items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));
        items.addAll(collectBlocks(exclusionPredicate.or(IS_BUILDING_BLOCK_PREDICATE.negate())));

        applyOrderings(items, orderings);
        outputAll(output, items);
    }

    private static void applyOrderings(List<Item> items, List<ItemOrdering> orderings) {
        for (ItemOrdering ordering : orderings) {
            int anchorIndex = items.indexOf(ordering.anchor());
            if (anchorIndex != -1) {
                Item item = ordering.item();
                int itemIndex = items.indexOf(item);
                if (itemIndex != -1) {
                    items.remove(itemIndex);
                    if (itemIndex < anchorIndex) {
                        anchorIndex--;
                    }
                }
                if (ordering.type() == ItemOrdering.Type.AFTER) {
                    items.add(anchorIndex + 1, item);
                } else {
                    items.add(anchorIndex, item);
                }
            }
        }
    }

    private static void outputAll(CreativeModeTab.Output output, List<Item> items) {
        for (Item item : items) {
            output.accept(item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private record ItemOrdering(Item item, Item anchor, Type type) {
        public static ItemOrdering before(Item item, Item anchor) {
            return new ItemOrdering(item, anchor, ItemOrdering.Type.BEFORE);
        }

        public static ItemOrdering after(Item item, Item anchor) {
            return new ItemOrdering(item, anchor, Type.AFTER);
        }

        public enum Type {
            BEFORE,
            AFTER
        }
    }
}
