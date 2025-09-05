package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.registry.LuncheonBlocks;
import net.vercte.luncheon.registry.LuncheonItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LuncheonStandardRecipeGen extends RecipeProvider {

    public LuncheonStandardRecipeGen(PackOutput output) { super(output); }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        shapedCrafting(consumer);
        cooking(consumer);
    }

    public static void shapedCrafting(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LuncheonBlocks.MECHANICAL_COOLER)
                .unlockedBy("has_propeller", InventoryChangeTrigger.TriggerInstance.hasItems(AllItems.PROPELLER.get()))
                .define('+', AllItems.PROPELLER)
                .define('s', AllTags.forgeItemTag("plates/iron"))
                .define('i', Items.BLUE_ICE)
                .define('|', AllBlocks.SHAFT)
                .pattern("s+s")
                .pattern("sis")
                .pattern(" | ")
                .save(consumer, Luncheon.asResource("crafting/mechanical_cooler"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, LuncheonItems.BAGUETTE_DOUGH)
                .unlockedBy("has_dough", InventoryChangeTrigger.TriggerInstance.hasItems(AllItems.DOUGH.get()))
                .define('d', AllTags.forgeItemTag("dough/wheat"))
                .pattern("ddd")
                .save(consumer, Luncheon.asResource("crafting/baguette_dough"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LuncheonBlocks.COBBLED_GLASS)
                .unlockedBy("has_glass_shard", InventoryChangeTrigger.TriggerInstance.hasItems(LuncheonItems.GLASS_SHARDS.get()))
                .define('s', LuncheonItems.GLASS_SHARDS.get())
                .pattern("ss")
                .pattern("ss")
                .save(consumer, Luncheon.asResource("crafting/cobbled_glass"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, LuncheonBlocks.LEMON_PLANKS, 4)
                .requires(LuncheonBlocks.LEMON_LOG)
                .unlockedBy("has_lemon_log", InventoryChangeTrigger.TriggerInstance.hasItems(LuncheonBlocks.LEMON_LOG.get()))
                .save(consumer, Luncheon.asResource("crafting/lemon_planks_from_lemon_log"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, LuncheonBlocks.LEMON_PLANKS, 4)
                .requires(LuncheonBlocks.STRIPPED_LEMON_LOG)
                .unlockedBy("has_stripped_lemon_log", InventoryChangeTrigger.TriggerInstance.hasItems(LuncheonBlocks.STRIPPED_LEMON_LOG.get()))
                .save(consumer, Luncheon.asResource("crafting/lemon_planks_from_stripped_lemon_log"));
    }

    public static void cooking(Consumer<FinishedRecipe> consumer) {
        smokingAndCampfire("wafer", LuncheonItems.RAW_WAFER, LuncheonItems.WAFER, consumer);
        smokingAndCampfire("baguette", LuncheonItems.BAGUETTE_DOUGH, LuncheonItems.BAGUETTE, consumer);
    }

    private static void smokingAndCampfire(String name, ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, Luncheon.asResource("smelting/" + name));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, Luncheon.asResource("campfire_cooking/" + name));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, Luncheon.asResource("smoking/" + name));
    }
}