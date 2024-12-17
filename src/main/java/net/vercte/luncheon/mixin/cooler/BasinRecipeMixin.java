package net.vercte.luncheon.mixin.cooler;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.vercte.luncheon.content.processing.cooler.CoolerBlock;
import net.vercte.luncheon.foundation.utility.data.recipe.mixin.LuncheonProcessingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BasinRecipe.class)
public abstract class BasinRecipeMixin extends ProcessingRecipe<SmartInventory> {
    public BasinRecipeMixin(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Inject(method = "apply(Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;Lnet/minecraft/world/item/crafting/Recipe;Z)Z", cancellable = true,
            at = @At(value = "NEW", target = "()Ljava/util/ArrayList;", ordinal = 0))
    private static void apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test, CallbackInfoReturnable<Boolean> cir) {
        if(recipe instanceof ProcessingRecipe<?>) {
            LuncheonProcessingRecipe cRecipe = (LuncheonProcessingRecipe)recipe;
            CoolerBlock.CoolingLevel cool = getCoolingLevelOf(basin.getLevel()
                    .getBlockState(basin.getBlockPos().below(1)));
            if(!cRecipe.luncheon$getRequiredCool().testCooler(cool))
                cir.setReturnValue(false);
        }
    }

    @Unique
    private static CoolerBlock.CoolingLevel getCoolingLevelOf(BlockState state) {
        if(state.hasProperty(CoolerBlock.COOL_LEVEL)) return state.getValue(CoolerBlock.COOL_LEVEL);
        return CoolerBlock.CoolingLevel.NONE;
    }
}
