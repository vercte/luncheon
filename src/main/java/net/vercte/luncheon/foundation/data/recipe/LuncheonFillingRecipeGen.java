package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonFluids;
import net.vercte.luncheon.content.registry.LuncheonItems;

@SuppressWarnings({"NullableProblems", "unused"})
public class LuncheonFillingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonFillingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe ICE_CREAM = create("ice_cream", b -> b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 250)
            .require(LuncheonItems.ICE_CREAM_CONE)
            .output(LuncheonItems.PLAIN_ICE_CREAM));

    GeneratedRecipe CHOCOLATE_ICE_CREAM = create("chocolate_ice_cream", b -> b.require(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 250)
            .require(LuncheonItems.ICE_CREAM_CONE)
            .output(LuncheonItems.CHOCOLATE_ICE_CREAM));

    GeneratedRecipe BERRY_ICE_CREAM = create("berry_ice_cream", b -> b.require(LuncheonFluids.BERRY_ICE_CREAM.get(), 250)
            .require(LuncheonItems.ICE_CREAM_CONE)
            .output(LuncheonItems.BERRY_ICE_CREAM));

    GeneratedRecipe ICE_CREAM_BLOCK = create("ice_cream_block", b -> b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
            .require(Blocks.SNOW_BLOCK)
            .output(LuncheonBlocks.PLAIN_ICE_CREAM_BLOCK));

    GeneratedRecipe CHOCOLATE_ICE_CREAM_BLOCK = create("chocolate_ice_cream_block", b -> b.require(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(Blocks.SNOW_BLOCK)
            .output(LuncheonBlocks.CHOCOLATE_ICE_CREAM_BLOCK));

    GeneratedRecipe BERRY_ICE_CREAM_BLOCK = create("berry_ice_cream_block", b -> b.require(LuncheonFluids.BERRY_ICE_CREAM.get(), 500)
            .require(Blocks.SNOW_BLOCK)
            .output(LuncheonBlocks.BERRY_ICE_CREAM_BLOCK));

    GeneratedRecipe POWDERED_SNOW = create("filling_powder_snow_bucket", b -> b.require(LuncheonFluids.POWDERED_SNOW.get(), 1000)
            .require(Items.BUCKET)
            .output(Items.POWDER_SNOW_BUCKET));

    GeneratedRecipe PLAIN_FILLED_WAFER_BLOCK = create("plain_filled_wafer_block", b -> b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
            .require(LuncheonBlocks.WAFER_BLOCK)
            .output(LuncheonBlocks.PLAIN_FILLED_WAFER_BLOCK));

    GeneratedRecipe CHOCOLATE_FILLED_WAFER_BLOCK = create("chocolate_filled_wafer_block", b -> b.require(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(LuncheonBlocks.WAFER_BLOCK)
            .output(LuncheonBlocks.CHOCOLATE_FILLED_WAFER_BLOCK));

    GeneratedRecipe BERRY_FILLED_WAFER_BLOCK = create("berry_filled_wafer_block", b -> b.require(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(LuncheonBlocks.WAFER_BLOCK)
            .output(LuncheonBlocks.BERRY_FILLED_WAFER_BLOCK));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.FILLING; }

    public static class DrainingRecipeGen extends LuncheonProcessingRecipeGen {
        public DrainingRecipeGen(PackOutput generator) { super(generator); }

        GeneratedRecipe POWDERED_SNOW_FROM_BUCKET = create("emptying_powder_snow_bucket", b -> b.require(Items.POWDER_SNOW_BUCKET)
                .output(LuncheonFluids.POWDERED_SNOW.get(), 1000)
                .output(Items.BUCKET));

        @Override
        protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.EMPTYING; }
    }
}
