package net.vercte.luncheon.mixin.cooler;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.jei.category.*;
import com.simibubi.create.compat.jei.category.animations.AnimatedMixer;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.vercte.luncheon.compat.jei.category.animations.AnimatedMechanicalCooler;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlock;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.foundation.utility.LuncheonLang;
import net.vercte.luncheon.foundation.data.recipe.mixin.LuncheonProcessingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasinCategory.class)
public abstract class BasinCategoryMixin extends CreateRecipeCategory<BasinRecipe> {
    public BasinCategoryMixin(Info<BasinRecipe> info) { super(info); }

    @Inject(method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;getRequiredHeat()Lcom/simibubi/create/content/processing/recipe/HeatCondition;"), cancellable = true, remap = false)
    private void addCoolerHint(IRecipeLayoutBuilder builder, BasinRecipe recipe, IFocusGroup focuses, CallbackInfo ci) {
        LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe) recipe;
        boolean requiresCool = lRecipe.luncheon$getRequiredCool() != CooledCondition.NONE;
        if(requiresCool) {
            builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 81)
                .addItemStack(LuncheonBlocks.MECHANICAL_COOLER.asStack());
            ci.cancel();
        }
    }

    @Definition(id = "requiredHeat", local = @Local(type = HeatCondition.class))
    @Definition(id = "NONE", field = "Lcom/simibubi/create/content/processing/recipe/HeatCondition;NONE:Lcom/simibubi/create/content/processing/recipe/HeatCondition;")
    @Expression("requiredHeat ==  NONE")
    @ModifyExpressionValue(method = "draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private boolean modifyRecipeHeatDisplay(boolean original, BasinRecipe recipe) {
        LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe) recipe;
        CooledCondition requiredCool = lRecipe.luncheon$getRequiredCool();
        return original && (requiredCool == CooledCondition.NONE);
    }

    @Inject(method = "draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"), cancellable = true)
    private void drawCooledCondition(BasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY, CallbackInfo ci) {
        LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe) recipe;
        CooledCondition requiredCool = lRecipe.luncheon$getRequiredCool();
        if(requiredCool != CooledCondition.NONE) {
            graphics.drawString(Minecraft.getInstance().font, LuncheonLang.translateDirect(requiredCool.getTranslationKey()), 9,
                    86, requiredCool.getColor(), false);
            ci.cancel();
        }
    }

    @Mixin(MixingCategory.class)
    public static abstract class MixingCategoryMixin extends BasinCategory {
        public MixingCategoryMixin(Info<BasinRecipe> info, boolean needsHeating) { super(info, needsHeating); }

        @Final
        @Shadow
        private AnimatedMixer mixer;

        @Unique
        private final AnimatedMechanicalCooler luncheon$cooler = new AnimatedMechanicalCooler();

        @Inject(method = "draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;getRequiredHeat()Lcom/simibubi/create/content/processing/recipe/HeatCondition;"), cancellable = true, remap = false)
        private void renderCooler(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY, CallbackInfo ci) {
            LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe) recipe;
            CooledCondition requiredCool = lRecipe.luncheon$getRequiredCool();
            if(requiredCool != CooledCondition.NONE) {
                luncheon$cooler.withCooling(MechanicalCoolerBlock.CoolingLevel.COOLED)
                                .draw(graphics, getBackground().getWidth() / 2 + 3, 55);
                mixer.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
                ci.cancel();
            }
        }
    }

    @Mixin(PackingCategory.class)
    public static abstract class PackingCategoryMixin extends BasinCategory {
        public PackingCategoryMixin(Info<BasinRecipe> info, boolean needsHeating) { super(info, needsHeating); }

        @Final
        @Shadow
        private AnimatedPress press;

        @Unique
        private final AnimatedMechanicalCooler luncheon$cooler = new AnimatedMechanicalCooler();

        @Inject(method = "draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;getRequiredHeat()Lcom/simibubi/create/content/processing/recipe/HeatCondition;"), cancellable = true, remap = false)
        private void renderCooler(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY, CallbackInfo ci) {
            LuncheonProcessingRecipe lRecipe = (LuncheonProcessingRecipe) recipe;
            CooledCondition requiredCool = lRecipe.luncheon$getRequiredCool();
            if(requiredCool != CooledCondition.NONE) {
                luncheon$cooler.withCooling(MechanicalCoolerBlock.CoolingLevel.COOLED)
                        .draw(graphics, getBackground().getWidth() / 2 + 3, 55);
                press.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
                ci.cancel();
            }
        }
    }
}
