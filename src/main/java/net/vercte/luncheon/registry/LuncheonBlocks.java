package net.vercte.luncheon.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlock;
import net.vercte.luncheon.registry.custom.LuncheonRegistrate;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class LuncheonBlocks {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    static {
        REGISTRATE.setCreativeTab(Luncheon.BASE_CREATIVE_TAB);
    }

    // region Kinetics
    public static final BlockEntry<MechanicalCoolerBlock> MECHANICAL_COOLER =
            REGISTRATE.block("mechanical_cooler", MechanicalCoolerBlock::new)
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY).lightLevel(MechanicalCoolerBlock::getLight))
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .lang("Mechanical Cooler")
                    .item()
                    .transform(customItemModel())
                    .onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 2.0))
                    .register();
    // endregion

    // region Building Blocks
    public static final BlockEntry<GlassBlock> COBBLED_GLASS = REGISTRATE.block("cobbled_glass", GlassBlock::new)
            .initialProperties(NonNullSupplier.of(() -> Blocks.GLASS))
            .tag(Tags.Blocks.GLASS_COLORLESS)
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .addLayer(() -> RenderType::cutoutMipped)
            .lang("Cobbled Glass")
            .item()
            .tag(Tags.Items.GLASS_COLORLESS)
            .build()
            .register();

    public static final BlockEntry<RotatedPillarBlock> PLAIN_ICE_CREAM_BLOCK = REGISTRATE.block("plain_ice_cream_block", RotatedPillarBlock::new)
            .properties(p ->
                p.mapColor(MapColor.SAND)
                        .strength(0.3F)
                        .sound(SoundType.SNOW)
                        .requiresCorrectToolForDrops())
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/palettes/ice_cream/plain")))
            .lang("Block of Plain Ice Cream")
            .item().tag(LuncheonTags.ItemTags.ICE_CREAM_BLOCKS_PLAIN.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> CHOCOLATE_ICE_CREAM_BLOCK = REGISTRATE.block("chocolate_ice_cream_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.SAND)
                            .strength(0.3F)
                            .sound(SoundType.SNOW)
                            .requiresCorrectToolForDrops())
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/palettes/ice_cream/chocolate")))
            .lang("Block of Chocolate Ice Cream")
            .item().tag(LuncheonTags.ItemTags.ICE_CREAM_BLOCKS_CHOCOLATE.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> BERRY_ICE_CREAM_BLOCK = REGISTRATE.block("berry_ice_cream_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.SAND)
                            .strength(0.3F)
                            .sound(SoundType.SNOW)
                            .requiresCorrectToolForDrops())
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/palettes/ice_cream/berry")))
            .lang("Block of Berry Ice Cream")
            .item().tag(LuncheonTags.ItemTags.ICE_CREAM_BLOCKS_BERRY.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> WAFER_BLOCK = REGISTRATE.block("wafer_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.TERRACOTTA_YELLOW)
                            .strength(0.2F)
                            .sound(SoundType.HANGING_ROOTS)
                            .requiresCorrectToolForDrops())
            .transform(TagGen.axeOnly())
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/empty_wafer_side"), Luncheon.asResource("block/wafer_top")))
            .lang("Empty Wafer Block")
            .item().transform(buildingBlock()).tag(LuncheonTags.ItemTags.WAFER_BLOCKS.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> PLAIN_FILLED_WAFER_BLOCK = REGISTRATE.block("plain_filled_wafer_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.TERRACOTTA_YELLOW)
                            .strength(0.2F)
                            .sound(SoundType.HANGING_ROOTS)
                            .requiresCorrectToolForDrops())
            .transform(TagGen.axeOnly())
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/plain_wafer_side"), Luncheon.asResource("block/wafer_top")))
            .lang("Filled Wafer Block")
            .item().transform(buildingBlock()).tag(LuncheonTags.ItemTags.WAFER_BLOCKS.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> CHOCOLATE_FILLED_WAFER_BLOCK = REGISTRATE.block("chocolate_filled_wafer_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.TERRACOTTA_YELLOW)
                            .strength(0.2F)
                            .sound(SoundType.HANGING_ROOTS)
                            .requiresCorrectToolForDrops())
            .transform(TagGen.axeOnly())
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/chocolate_wafer_side"), Luncheon.asResource("block/wafer_top")))
            .lang("Chocolate Filled Wafer Block")
            .item().transform(buildingBlock()).tag(LuncheonTags.ItemTags.WAFER_BLOCKS.tag)
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> BERRY_FILLED_WAFER_BLOCK = REGISTRATE.block("berry_filled_wafer_block", RotatedPillarBlock::new)
            .properties(p ->
                    p.mapColor(MapColor.TERRACOTTA_YELLOW)
                            .strength(0.2F)
                            .sound(SoundType.HANGING_ROOTS)
                            .requiresCorrectToolForDrops())
            .transform(TagGen.axeOnly())
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/berry_wafer_side"), Luncheon.asResource("block/wafer_top")))
            .lang("Berry Filled Wafer Block")
            .item().transform(buildingBlock()).tag(LuncheonTags.ItemTags.WAFER_BLOCKS.tag)
            .build().register();
    // endregion

    // region Lemon Wood
    public static final BlockEntry<RotatedPillarBlock> LEMON_LOG = REGISTRATE.block("lemon_log", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.BIRCH_LOG)
            .properties(p -> p.mapColor(MapColor.STONE))
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/lemon_log")))
            .transform(TagGen.axeOnly()).transform(logBlock())
            .tag(BlockTags.SNAPS_GOAT_HORN, BlockTags.OVERWORLD_NATURAL_LOGS, BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)
            .item()
            .transform(buildingBlock()).transform(logItem())
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> LEMON_WOOD = REGISTRATE.block("lemon_wood", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.BIRCH_LOG)
            .properties(p -> p.mapColor(MapColor.STONE))
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/lemon_log_side"), Luncheon.asResource("block/lemon_log_side")))
            .transform(TagGen.axeOnly()).transform(logBlock())
            .tag(BlockTags.SNAPS_GOAT_HORN, BlockTags.OVERWORLD_NATURAL_LOGS, BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)
            .item()
            .transform(buildingBlock()).transform(logItem())
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> STRIPPED_LEMON_LOG = REGISTRATE.block("stripped_lemon_log", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_LOG)
            .properties(p -> p.mapColor(MapColor.SAND))
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/stripped_lemon_log")))
            .transform(TagGen.axeOnly()).transform(logBlock())
            .item()
            .tag(AllTags.AllItemTags.STRIPPED_LOGS.tag, AllTags.AllItemTags.MODDED_STRIPPED_LOGS.tag)
            .transform(buildingBlock()).transform(logItem())
            .build().register();

    public static final BlockEntry<RotatedPillarBlock> STRIPPED_LEMON_WOOD = REGISTRATE.block("stripped_lemon_wood", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_LOG)
            .properties(p -> p.mapColor(MapColor.SAND))
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/stripped_lemon_log_side"), Luncheon.asResource("block/stripped_lemon_log_side")))
            .transform(TagGen.axeOnly()).transform(logBlock())
            .item()
            .tag(AllTags.AllItemTags.STRIPPED_LOGS.tag, AllTags.AllItemTags.MODDED_STRIPPED_LOGS.tag)
            .transform(buildingBlock()).transform(logItem())
            .build().register();

    public static final BlockEntry<Block> LEMON_PLANKS = REGISTRATE.block("lemon_planks", Block::new)
            .initialProperties(() -> Blocks.BIRCH_PLANKS)
            .properties(p -> p.mapColor(MapColor.SAND))
            .transform(TagGen.axeOnly())
            .tag(BlockTags.PLANKS)
            .item()
            .tag(ItemTags.PLANKS)
            .transform(buildingBlock())
            .build().register();

    public static final BlockEntry<StairBlock> LEMON_STAIRS = REGISTRATE.block("lemon_stairs", p -> new StairBlock(Blocks.BIRCH_STAIRS::defaultBlockState, p))
            .initialProperties(() -> Blocks.BIRCH_STAIRS)
            .properties(p -> p.mapColor(MapColor.SAND))
            .blockstate((c, p) -> p.stairsBlock(c.get(), Luncheon.asResource("block/lemon_planks")))
            .transform(TagGen.axeOnly())
            .tag(BlockTags.STAIRS, BlockTags.WOODEN_STAIRS)
            .item()
            .tag(ItemTags.STAIRS, ItemTags.WOODEN_STAIRS)
            .transform(buildingBlock())
            .build().register();

    public static final BlockEntry<SlabBlock> LEMON_SLAB = REGISTRATE.block("lemon_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.BIRCH_STAIRS)
            .properties(p -> p.mapColor(MapColor.SAND))
            .blockstate((c, p) -> p.slabBlock(c.get(), Luncheon.asResource("block/lemon_planks"), Luncheon.asResource("block/lemon_planks")))
            .transform(TagGen.axeOnly())
            .tag(BlockTags.SLABS, BlockTags.WOODEN_SLABS)
            .item()
            .tag(ItemTags.SLABS, ItemTags.WOODEN_SLABS)
            .transform(buildingBlock())
            .build().register();



    // endregion

    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> logBlock() {
        return b -> b.tag(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, BlockTags.COMPLETES_FIND_TREE_TUTORIAL).tag();
    }

    public static <T extends Item, P> NonNullFunction<ItemBuilder<T, P>, ItemBuilder<T, P>> logItem() {
        return b -> b.tag(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
    }

    public static <T extends Item, P> NonNullFunction<ItemBuilder<T, P>, ItemBuilder<T, P>> buildingBlock() {
        return b -> b.tag(LuncheonTags.ItemTags.BUILDING_BLOCKS.tag);
    }

    public static void register() {}
}
