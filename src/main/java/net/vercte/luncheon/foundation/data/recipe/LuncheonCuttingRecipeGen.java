package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.content.registry.LuncheonItems;

public class LuncheonCuttingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonCuttingRecipeGen(PackOutput generator) {
        super(generator);
    }

    GeneratedRecipe BREAD_SLICE = create("bread_slice", b -> b.require(Items.BREAD)
            .duration(5)
            .output(LuncheonItems.BREAD_SLICE, 2));

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.CUTTING;
    }
}
