package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.vercte.luncheon.registry.LuncheonItems;

@SuppressWarnings({"NullableProblems", "unused"})
public class LuncheonPressingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonPressingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe ICE_CREAM_CONE = create("ice_cream_cone", b ->
            b.require(LuncheonItems.WAFER).output(LuncheonItems.ICE_CREAM_CONE, 2));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.PRESSING; }
}
