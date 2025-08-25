package net.vercte.luncheon.content.processing.centrifugation;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.vercte.luncheon.registry.LuncheonRecipeTypes;

public class CentrifugationRecipe extends BasinRecipe {
    public CentrifugationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(LuncheonRecipeTypes.CENTRIFUGATION, params);
    }
}
