package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.registry.LuncheonFluids;
import net.vercte.luncheon.registry.LuncheonHeatConditions;

@SuppressWarnings({"NullableProblems", "unused"})
public class LuncheonMixingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonMixingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe PLAIN_ICE_CREAM = create("plain_ice_cream", b ->
            b.require(Tags.Fluids.MILK, 250)
                    .require(Items.SUGAR)
                    .require(LuncheonFluids.POWDERED_SNOW.get(), 500)
                    .requiresHeat(LuncheonHeatConditions.COOLED)
                    .output(LuncheonFluids.PLAIN_ICE_CREAM.get(), 250));

    GeneratedRecipe CHOCOLATE_ICE_CREAM = create("chocolate_ice_cream", b ->
                    b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
                            .require(AllFluids.CHOCOLATE.get(), 100)
                            .requiresHeat(LuncheonHeatConditions.COOLED)
                            .output(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 500));

    GeneratedRecipe BERRY_ICE_CREAM = create("berry_ice_cream", b ->
                    b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
                            .require(LuncheonFluids.BERRY_EXTRACT.get(), 100)
                            .requiresHeat(LuncheonHeatConditions.COOLED)
                            .output(LuncheonFluids.BERRY_ICE_CREAM.get(), 500));

    GeneratedRecipe POWDERED_SNOW = create("powdered_snow", b ->
                    b.require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .requiresHeat(LuncheonHeatConditions.COOLED)
                            .output(LuncheonFluids.POWDERED_SNOW.get(), 1000));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MIXING; }
}
