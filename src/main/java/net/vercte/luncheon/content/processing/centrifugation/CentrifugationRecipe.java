package net.vercte.luncheon.content.processing.centrifugation;

import com.simibubi.create.api.recipe.HeatCondition;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.vercte.luncheon.registry.LuncheonRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class CentrifugationRecipe extends BasinRecipe {
    public CentrifugationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(LuncheonRecipeTypes.CENTRIFUGATION, params);
    }

    @Override
    protected boolean canRequireHeat() {
        return false;
    }

    @Override
    public @Nullable HeatCondition getRequiredHeat() { return null; }

    @Override
    public int getProcessingDuration() {
        return super.getProcessingDuration();
    }
}
