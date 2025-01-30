package net.vercte.luncheon.foundation.data.recipe.mixin;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;

public interface LuncheonProcessingRecipeBuilder<T extends ProcessingRecipe<?>> {
    ProcessingRecipeBuilder<T> luncheon$requiresCool(CooledCondition cool);
}
