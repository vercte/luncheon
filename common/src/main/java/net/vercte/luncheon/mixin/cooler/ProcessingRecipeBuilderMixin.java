package net.vercte.luncheon.mixin.cooler;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.foundation.data.recipe.mixin.LuncheonProcessingRecipeBuilder;
import net.vercte.luncheon.foundation.data.recipe.mixin.LuncheonProcessingRecipeParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unchecked")
@Mixin(ProcessingRecipeBuilder.class)
public abstract class ProcessingRecipeBuilderMixin<T extends ProcessingRecipe<?>> implements LuncheonProcessingRecipeBuilder<T> {
    @Shadow
    protected ProcessingRecipeBuilder.ProcessingRecipeParams params;

    @Unique
    public ProcessingRecipeBuilder<T> luncheon$requiresCool(CooledCondition condition) {
        ((LuncheonProcessingRecipeParams)params).luncheon$setRequiredCool(condition);
        return (ProcessingRecipeBuilder<T>)(Object)this;
    }

    @Mixin(ProcessingRecipeBuilder.ProcessingRecipeParams.class)
    public abstract static class ProcessingRecipeParamsMixin implements LuncheonProcessingRecipeParams {
        @Unique
        protected CooledCondition luncheon$requiredCool;
        public CooledCondition luncheon$getRequiredCool() { return luncheon$requiredCool; }
        public void luncheon$setRequiredCool(CooledCondition cool) { luncheon$requiredCool = cool; }


        @Inject(method = "<init>", at = @At("TAIL"))
        public void ProcessingRecipeParamsConstructor(ResourceLocation id, CallbackInfo ci) {
            luncheon$requiredCool = CooledCondition.NONE;
        }
    }
}
