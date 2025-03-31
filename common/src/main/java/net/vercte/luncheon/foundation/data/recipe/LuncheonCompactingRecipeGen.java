package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonFluids;
import net.vercte.luncheon.content.registry.LuncheonItems;

@SuppressWarnings({"NullableProblems", "unused"})
public class LuncheonCompactingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonCompactingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe ICE_CUBE = createCooled("ice_cube", b ->
            b.require(Fluids.WATER, 250)
             .output(LuncheonItems.ICE_CUBE, 1),
            CooledCondition.COOLED);

    GeneratedRecipe ICE_FROM_ICE_CUBE = createCooled("ice_from_ice_cube", b ->
            b.require(LuncheonItems.ICE_CUBE).require(LuncheonItems.ICE_CUBE)
             .require(LuncheonItems.ICE_CUBE).require(LuncheonItems.ICE_CUBE)
             .output(Items.ICE, 1),
            CooledCondition.COOLED);

    GeneratedRecipe BERRY_EXTRACT = create("berry_extract", b ->
            b.require(() -> Items.SWEET_BERRIES)
                    .require(() -> Items.SWEET_BERRIES)
                    .require(() -> Items.SWEET_BERRIES)
                    .require(() -> Items.SWEET_BERRIES)
                    .output(LuncheonFluids.BERRY_EXTRACT.get(), 100)
    );

    GeneratedRecipe RAW_WAFER = create("raw_wafer", b ->
            b.require(AllItems.WHEAT_FLOUR)
                    .require(Fluids.WATER, 100)
                    .output(LuncheonItems.RAW_WAFER, 2));

    GeneratedRecipe WAFER_BLOCK = create("wafer_block", b ->
            b.require(LuncheonItems.WAFER)
                    .require(LuncheonItems.WAFER)
                    .require(LuncheonItems.WAFER)
                    .require(LuncheonItems.WAFER)
                    .output(LuncheonBlocks.WAFER_BLOCK));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.COMPACTING; }
}
