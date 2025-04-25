package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.registry.LuncheonItems;

public class LuncheonCuttingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonCuttingRecipeGen(PackOutput generator) {
        super(generator);
    }

    GeneratedRecipe BREAD_SLICE = create("bread_slice", b -> b.require(Items.BREAD)
            .duration(5)
            .output(LuncheonItems.BREAD_SLICE, 2));

    GeneratedRecipe BREAD_SLICE_FROM_BAGUETTE = create("bread_slice_from_baguette", b -> b.require(LuncheonItems.BAGUETTE)
            .duration(10)
            .output(LuncheonItems.BREAD_SLICE, 6));

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.CUTTING;
    }
}
