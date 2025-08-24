package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.registry.LuncheonItems;

@SuppressWarnings("unused")
public class LuncheonCuttingRecipeGen extends CuttingRecipeGen {
    public LuncheonCuttingRecipeGen(PackOutput generator) { super(generator, "luncheon"); }

    GeneratedRecipe BREAD_SLICE = create("bread_slice", b -> b.require(Items.BREAD)
            .duration(5)
            .output(LuncheonItems.BREAD_SLICE, 2));

    GeneratedRecipe BREAD_SLICE_FROM_BAGUETTE = create("bread_slice_from_baguette", b -> b.require(LuncheonItems.BAGUETTE)
            .duration(10)
            .output(LuncheonItems.BREAD_SLICE, 6));

}
