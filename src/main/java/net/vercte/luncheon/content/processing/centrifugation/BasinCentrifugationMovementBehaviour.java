package net.vercte.luncheon.content.processing.centrifugation;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.content.contraptions.bearing.BearingContraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.vercte.luncheon.LuncheonConfig;
import net.vercte.luncheon.registry.LuncheonRecipeTypes;

import java.util.*;
import java.util.function.Predicate;

public class BasinCentrifugationMovementBehaviour {
    private static final Object centrifugeRecipesKey = new Object();

    private static final String PROGRESS_TAG = "luncheon$centrifuge_progress";

    public static void tick(MovementContext context) {
        if(context.contraption instanceof BearingContraption bearing) {
            if(bearing.getFacing() != Direction.UP) return;

            if(context.world.isClientSide()) {
                int i = 0;
                i += 0;
            }

            double speed = Math.min(context.motion.length(), LuncheonConfig.maxCentrifugeSpeed);

            int newProgress = context.data.getInt(PROGRESS_TAG);
            if(speed > LuncheonConfig.minCentrifugeSpeed) {
                newProgress += (int) Math.round(speed * 2);
            }

            BasinBlockEntity basin = getBasin(context);

            List<? extends Recipe<?>> recipes = getRecipes(context, basin);

            int recipeProgress = newProgress;
            Optional<? extends Recipe<?>> foundRecipe = recipes.stream().filter(r -> {
                if(r instanceof CentrifugationRecipe cRecipe) {
                    return cRecipe.getProcessingDuration() <= recipeProgress;
                }
                return false;
            }).findFirst();

            if(foundRecipe.isPresent()) {
                Recipe<?> recipe = foundRecipe.get();
                if(recipe instanceof CentrifugationRecipe cRecipe) {
                    newProgress -= cRecipe.getProcessingDuration();
                    BasinRecipe.apply(basin, cRecipe);
                }
            }

            basin.write(context.blockEntityData, false);
            context.data.putInt(PROGRESS_TAG, newProgress);
            updateForClients(context);
        }
    }

    private static void updateForClients(MovementContext context) {
        if(!context.world.isClientSide()) return;
        BlockEntity beHere = context.contraption.presentBlockEntities.get(context.localPos);

        BasinBlockEntity basin;
        if(beHere instanceof BasinBlockEntity) basin = (BasinBlockEntity)beHere;
        else return;

        basin.load(context.blockEntityData);
    }

    private static BasinBlockEntity getBasin(MovementContext context) {
        BasinBlockEntity basin = new BasinBlockEntity(AllBlockEntityTypes.BASIN.get(), context.localPos, context.state);
        basin.load(context.blockEntityData);
        basin.setLevel(context.contraption.getContraptionWorld());
        return basin;
    }

    private static List<? extends Recipe<?>> getRecipes(MovementContext context, BasinBlockEntity basin) {
        Predicate<Recipe<?>> types = RecipeConditions.isOfType(LuncheonRecipeTypes.CENTRIFUGATION.getType());

        List<Recipe<?>> list = new ArrayList<>();
        for (Recipe<?> r : RecipeFinder.get(centrifugeRecipesKey, context.world, types)) {
            if (matchBasinRecipe(basin, r)) list.add(r);
        }

        list.sort((r1, r2) -> r2.getIngredients().size() - r1.getIngredients().size());
        return list;
    }

    protected static <C extends Container> boolean matchBasinRecipe(BasinBlockEntity basin, Recipe<C> recipe) {
        if (recipe == null)
            return false;

        return BasinRecipe.match(basin, recipe);
    }
}
