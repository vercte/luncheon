package net.vercte.luncheon.mixin.cooler;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.foundation.utility.data.recipe.mixin.LuncheonProcessingRecipe;
import net.vercte.luncheon.foundation.utility.data.recipe.mixin.LuncheonProcessingRecipeParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProcessingRecipe.class)
public abstract class ProcessingRecipeMixin<T extends Container> implements Recipe<T>, LuncheonProcessingRecipe {
    @Unique
    protected CooledCondition luncheon$requiredCool;

    public CooledCondition luncheon$getRequiredCool() {
        return luncheon$requiredCool;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void ProcessingRecipeConstructor(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params, CallbackInfo ci) {
        luncheon$requiredCool = ((LuncheonProcessingRecipeParams) params).luncheon$getRequiredCool();
    }
}
