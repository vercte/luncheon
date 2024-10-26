package net.vercte.luncheon.content.kinetics.stirrer;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.vercte.luncheon.content.registry.LuncheonRecipeTypes;

public class StirringRecipe extends BasinRecipe {
    public StirringRecipe(ProcessingRecipeParams params) {
        super(LuncheonRecipeTypes.STIRRING, params);
    }
}
