package net.vercte.luncheon.foundation.utility.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonFluids;
import net.vercte.luncheon.content.registry.LuncheonItems;

public class LuncheonFillingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonFillingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe ICE_CREAM = create("ice_cream", b -> b.require(LuncheonFluids.ICE_CREAM.get(), 250)
            .require(LuncheonItems.ICE_CREAM_CONE)
            .output(LuncheonItems.PLAIN_ICE_CREAM));

    GeneratedRecipe ICE_CREAM_BLOC = create("ice_cream_block", b -> b.require(LuncheonFluids.ICE_CREAM.get(), 500)
            .require(Blocks.SNOW_BLOCK)
            .output(LuncheonBlocks.ICE_CREAM_BLOCK));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.FILLING; }
}
