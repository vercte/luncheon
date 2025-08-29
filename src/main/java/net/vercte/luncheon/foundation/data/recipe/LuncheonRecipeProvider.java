package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.vercte.luncheon.Luncheon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings({"unused"})
public class LuncheonRecipeProvider extends RecipeProvider {
    protected static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();
    protected final List<GeneratedRecipe> all = new ArrayList<>();

    public LuncheonRecipeProvider(PackOutput output) {
        super(output);
    }

    public static void registerAllProcessing(DataGenerator gen, PackOutput output) {
        GENERATORS.add(new LuncheonPressingRecipeGen(output));
        GENERATORS.add(new LuncheonCompactingRecipeGen(output));
        GENERATORS.add(new LuncheonMixingRecipeGen(output));
        GENERATORS.add(new LuncheonCrushingRecipeGen(output));
        GENERATORS.add(new LuncheonCrushingRecipeGen.Milling(output));
        GENERATORS.add(new LuncheonFillingRecipeGen(output));
        GENERATORS.add(new LuncheonFillingRecipeGen.Emptying(output));
        GENERATORS.add(new LuncheonCuttingRecipeGen(output));
        GENERATORS.add(new LuncheonCentrifugationRecipeGen(output));

        gen.addProvider(true, new DataProvider() {
            @Override
            @NotNull
            public CompletableFuture<?> run(@NotNull CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }

            @Override
            @NotNull
            public String getName() {
                return "Luncheon's Processing Recipes";
            }
        });
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        all.forEach(c -> c.register(consumer));
        Luncheon.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    @FunctionalInterface
    public interface GeneratedRecipe {
        void register(Consumer<FinishedRecipe> consumer);
    }

    protected static class Marker {}
}
