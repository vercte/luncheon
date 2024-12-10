package net.vercte.luncheon.content.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.block.BlazeCakeBlock;
import net.vercte.luncheon.content.block.BuddingChiliCropBlock;
import net.vercte.luncheon.content.block.ChiliCropBlock;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlock;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;
import net.vercte.luncheon.foundation.utility.data.LuncheonBlockstates;

import java.util.Arrays;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class LuncheonBlocks {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    // region Kinetics
    public static final BlockEntry<MechanicalStirrerBlock> MECHANICAL_STIRRER =
            REGISTRATE.block("mechanical_stirrer", MechanicalStirrerBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .transform(axeOrPickaxe())
                    .item(AssemblyOperatorBlockItem::new)
                    .transform(customItemModel())
                    .lang("Mechanical Stirrer")
                    .register();
    // endregion

    // region Placeable Foods
    public static final BlockEntry<BlazeCakeBlock> BLAZE_CAKE =
            REGISTRATE.block("blaze_cake", BlazeCakeBlock::new)
                    .initialProperties(NonNullSupplier.of(() -> Blocks.CAKE))
                    .blockstate(LuncheonBlockstates::PieBlock)
                    .lang("Blaze Cake")
                    .register();
    // endregion

    // region Crops
    public static final BlockEntry<BuddingChiliCropBlock> BUDDING_CHILI_CROP = REGISTRATE.block("budding_chili", BuddingChiliCropBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .blockstate((c, p) -> LuncheonBlockstates.HiddenStageBlock(c, p, LuncheonBlockstates.fdResourceBlock("crop_cross"), "cross", BuddingChiliCropBlock.AGE, Arrays.asList(0, 1, 2, 3, 3)))
            .tag(BlockTags.CROPS)
            .register();

    public static final BlockEntry<ChiliCropBlock> CHILI_CROP = REGISTRATE.block("chili", ChiliCropBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .blockstate((c, p) -> LuncheonBlockstates.stageBlock(c, p, ChiliCropBlock.VINE_AGE))
            .tag(BlockTags.CROPS)
            .register();
    // endregion

    public static void register() {}
}
