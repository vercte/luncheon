package net.vercte.luncheon.content.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.block.BlazeCakeBlock;
import net.vercte.luncheon.content.block.BuddingFireberryBlock;
import net.vercte.luncheon.content.block.FireberryVineBlock;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlock;
import net.vercte.luncheon.foundation.utility.data.LuncheonBlockstates;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.Arrays;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class LuncheonBlocks {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

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
    public static final BlockEntry<BuddingFireberryBlock> BUDDING_FIREBERRY_CROP = REGISTRATE.block("budding_fireberry", BuddingFireberryBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .blockstate((c, p) -> LuncheonBlockstates.HiddenStageBlock(c, p, LuncheonBlockstates.fdResourceBlock("crop_cross"), "cross", BuddingFireberryBlock.AGE, Arrays.asList(0, 1, 2, 3, 3)))
            .tag(BlockTags.CROPS)
            .register();

    public static final BlockEntry<FireberryVineBlock> FIREBERRY_CROP = REGISTRATE.block("fireberry", FireberryVineBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .blockstate((c, p) -> LuncheonBlockstates.VineCropBlock(c, p, FireberryVineBlock.VINE_AGE, FireberryVineBlock.ROPELOGGED))
            .tag(BlockTags.CROPS)
            .register();
    // endregion

    public static void register() {}
}
