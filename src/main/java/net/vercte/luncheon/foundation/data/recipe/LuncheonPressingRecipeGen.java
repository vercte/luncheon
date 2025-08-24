package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import net.minecraft.data.PackOutput;
import net.vercte.luncheon.registry.LuncheonItems;

@SuppressWarnings("unused")
public class LuncheonPressingRecipeGen extends PressingRecipeGen {
    public LuncheonPressingRecipeGen(PackOutput generator) { super(generator, "luncheon"); }

    GeneratedRecipe ICE_CREAM_CONE = create("ice_cream_cone", b ->
            b.require(LuncheonItems.WAFER).output(LuncheonItems.ICE_CREAM_CONE, 2));
}
