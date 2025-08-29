package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.registry.LuncheonItems;
import net.vercte.luncheon.registry.LuncheonRecipeTypes;

@SuppressWarnings("unused")
public class LuncheonCentrifugationRecipeGen extends ProcessingRecipeGen {
    public LuncheonCentrifugationRecipeGen(PackOutput generator) { super(generator, "luncheon"); }

    GeneratedRecipe BUTTER = create("butter", b ->
            b.require(Tags.Fluids.MILK, 250)
                    .duration(150)
                    .output(LuncheonItems.BUTTER));


    @Override
    protected LuncheonRecipeTypes getRecipeType() {
        return LuncheonRecipeTypes.CENTRIFUGATION;
    }
}
