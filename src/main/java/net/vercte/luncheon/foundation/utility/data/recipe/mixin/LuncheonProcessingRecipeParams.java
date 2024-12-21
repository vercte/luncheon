package net.vercte.luncheon.foundation.utility.data.recipe.mixin;

import net.vercte.luncheon.content.processing.recipe.CooledCondition;

public interface LuncheonProcessingRecipeParams {
    void luncheon$setRequiredCool(CooledCondition cool);
    CooledCondition luncheon$getRequiredCool();
}
