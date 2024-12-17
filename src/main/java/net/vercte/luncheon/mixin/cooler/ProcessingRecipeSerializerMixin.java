package net.vercte.luncheon.mixin.cooler;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.foundation.utility.data.recipe.mixin.LuncheonProcessingRecipe;
import net.vercte.luncheon.foundation.utility.data.recipe.mixin.LuncheonProcessingRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProcessingRecipeSerializer.class)
public abstract class ProcessingRecipeSerializerMixin<T extends ProcessingRecipe<?>> implements RecipeSerializer<T> {
    @Inject(method = "writeToJson", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/recipe/ProcessingRecipe;writeAdditional(Lcom/google/gson/JsonObject;)V"), remap = false)
    protected void writeToJson(JsonObject json, T recipe, CallbackInfo ci) {
        LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe)recipe;
        CooledCondition requiredCool = lRecipe.luncheon$getRequiredCool();
        if (requiredCool == CooledCondition.COOLED)
            json.addProperty("coolRequirement", requiredCool.serialize());
    }

    @ModifyReceiver(
            method = "readFromJson", remap = false,
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/recipe/ProcessingRecipeBuilder;build()Lcom/simibubi/create/content/processing/recipe/ProcessingRecipe;", remap = false)
    )
    protected ProcessingRecipeBuilder<T> readFromJson(ProcessingRecipeBuilder<T> builder, ResourceLocation recipeId, JsonObject json) {
        LuncheonProcessingRecipeBuilder<T> lBuilder = (LuncheonProcessingRecipeBuilder<T>)builder;

        if (GsonHelper.isValidNode(json, "coolRequirement"))
            lBuilder.luncheon$requiresCool(CooledCondition.deserialize(GsonHelper.getAsString(json, "coolRequirement")));
        return builder;
    }
}
