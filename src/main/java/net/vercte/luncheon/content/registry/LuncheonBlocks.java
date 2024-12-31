package net.vercte.luncheon.content.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.block.BlazeCakeBlock;
import net.vercte.luncheon.content.block.BuddingChiliCropBlock;
import net.vercte.luncheon.content.block.ChiliCropBlock;
import net.vercte.luncheon.content.processing.cooler.CoolerBlock;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;
import net.vercte.luncheon.foundation.utility.data.LuncheonBlockstates;

import java.util.Arrays;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class LuncheonBlocks {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    static {
        REGISTRATE.setCreativeTab(Luncheon.BASE_CREATIVE_TAB);
    }

    // region Kinetics
    public static final BlockEntry<CoolerBlock> MECHANICAL_COOLER =
            REGISTRATE.block("mechanical_cooler", CoolerBlock::new)
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY).lightLevel(CoolerBlock::getLight))
                    .transform(pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(2.0))
                    .lang("Mechanical Cooler")
                    .item()
                    .transform(customItemModel())
                    .register();
    // endregion

    // region Placeable Foods
    public static final BlockEntry<BlazeCakeBlock> BLAZE_CAKE =
            REGISTRATE.block("blaze_cake", BlazeCakeBlock::new)
                    .initialProperties(NonNullSupplier.of(() -> Blocks.CAKE))
                    .blockstate(LuncheonBlockstates::PieBlock)
                    .loot((lt, block) -> {
                        lt.add(block, BlazeCakeBlock.buildLootTable());
                    })
                    .lang("Blaze Cake")
                    .register();
    // endregion

    // region Crops
    public static final BlockEntry<BuddingChiliCropBlock> BUDDING_CHILI_CROP =
            REGISTRATE.block("budding_chili", BuddingChiliCropBlock::new)
                    .initialProperties(() -> Blocks.WHEAT)
                    .blockstate((c, p) -> LuncheonBlockstates.HiddenStageBlock(c, p, LuncheonBlockstates.fdResourceBlock("crop_cross"), "cross", BuddingChiliCropBlock.AGE, Arrays.asList(0, 1, 2, 3, 3)))
                    .tag(BlockTags.CROPS)
                    .register();

    public static final BlockEntry<ChiliCropBlock> CHILI_CROP =
            REGISTRATE.block("chili", ChiliCropBlock::new)
                    .initialProperties(() -> Blocks.WHEAT)
                    .blockstate((c, p) -> LuncheonBlockstates.stageBlock(c, p, ChiliCropBlock.VINE_AGE))
                    .tag(BlockTags.CROPS)
                    .register();
    // endregion

    // region Building Blocks
    public static final BlockEntry<GlassBlock> COBBLED_GLASS = REGISTRATE.block("cobbled_glass", GlassBlock::new)
            .initialProperties(NonNullSupplier.of(() -> Blocks.GLASS))
            .tag(Tags.Blocks.GLASS_COLORLESS)
            .loot((t, g) -> t.dropWhenSilkTouch(g))
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(LuncheonBlockstates::cubeAll)
            .lang("Cobbled Glass")
            .item()
            .tag(Tags.Items.GLASS_COLORLESS)
            .build()
            .register();

    public static final BlockEntry<RotatedPillarBlock> ICE_CREAM_BLOCK = REGISTRATE.block("ice_cream_block", RotatedPillarBlock::new)
            .initialProperties(NonNullSupplier.of(() -> Blocks.WHITE_WOOL))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate((c, p) -> p.axisBlock(c.get()))
            .lang("Block of Ice Cream")
            .item().build().register();

    public static final BlockEntry<Block> ICE_CREAM_BRICKS = REGISTRATE.block("ice_cream_bricks", Block::new)
            .initialProperties(NonNullSupplier.of(() -> Blocks.WHITE_WOOL))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate(LuncheonBlockstates::cubeAll)
            .lang("Ice Cream Bricks")
            .item().build().register();

    public static final BlockEntry<Block> POLISHED_ICE_CREAM = REGISTRATE.block("polished_ice_cream", Block::new)
            .initialProperties(NonNullSupplier.of(() -> Blocks.WHITE_WOOL))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate(LuncheonBlockstates::cubeAll)
            .lang("Polished Ice Cream")
            .item().build().register();

    // endregion

    public static void register() {}
}
