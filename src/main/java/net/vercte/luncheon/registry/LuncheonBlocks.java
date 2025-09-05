package net.vercte.luncheon.registry;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlock;
import net.vercte.luncheon.registry.custom.LuncheonRegistrate;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class LuncheonBlocks {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    static {
        REGISTRATE.setCreativeTab(Luncheon.BASE_CREATIVE_TAB);
    }

    // region Kinetics
    public static final BlockEntry<MechanicalCoolerBlock> MECHANICAL_COOLER =
            REGISTRATE.block("mechanical_cooler", MechanicalCoolerBlock::new)
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY).lightLevel(MechanicalCoolerBlock::getLight))
                    .transform(pickaxeOnly())
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
            .transform(axeOrPickaxe())
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
            .transform(axeOrPickaxe())
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
            .transform(axeOrPickaxe())
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
            .transform(axeOrPickaxe())
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
            .item().build()
            .register();

    public static final BlockEntry<RotatedPillarBlock> STRIPPED_LEMON_LOG = REGISTRATE.block("stripped_lemon_log", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_LOG)
            .properties(p -> p.mapColor(MapColor.SAND))
            .blockstate((c, p) -> p.axisBlock(c.get(), Luncheon.asResource("block/stripped_lemon_log")))
            .item().build()
            .register();

    public static final BlockEntry<Block> LEMON_PLANKS = REGISTRATE.block("lemon_planks", Block::new)
            .initialProperties(() -> Blocks.BIRCH_PLANKS)
            .properties(p -> p.mapColor(MapColor.SAND))
            .item().build()
            .register();

    // endregion

    public static <T extends Item, P> NonNullFunction<ItemBuilder<T, P>, ItemBuilder<T, P>> buildingBlock() {
        return b -> b.tag(LuncheonTags.ItemTags.BUILDING_BLOCKS.tag);
    }

    public static void register() {}
}
