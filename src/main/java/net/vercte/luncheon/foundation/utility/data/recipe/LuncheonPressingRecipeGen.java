package net.vercte.luncheon.foundation.utility.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.vercte.luncheon.content.registry.LuncheonItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class LuncheonPressingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonPressingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe RAW_WAFER = create("raw_wafer", b ->
            b.require(ForgeTags.DOUGH).output(LuncheonItems.RAW_WAFER));

    GeneratedRecipe ICE_CREAM_CONE = create("ice_cream_cone", b ->
            b.require(LuncheonItems.WAFER).output(LuncheonItems.ICE_CREAM_CONE));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.PRESSING; }
}
