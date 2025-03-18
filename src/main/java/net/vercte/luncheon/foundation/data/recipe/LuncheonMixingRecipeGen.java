package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.content.registry.LuncheonFluids;

@SuppressWarnings({"NullableProblems", "unused"})
public class LuncheonMixingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonMixingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe PLAIN_ICE_CREAM = createCooled("plain_ice_cream", b ->
            b.require(Tags.Fluids.MILK, 250)
                    .require(Items.SUGAR)
                    .require(LuncheonFluids.POWDERED_SNOW.get(), 500)
                    .output(LuncheonFluids.PLAIN_ICE_CREAM.get(), 250),
            CooledCondition.COOLED);

    GeneratedRecipe CHOCOLATE_ICE_CREAM = createCooled("chocolate_ice_cream", b ->
                    b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
                            .require(AllFluids.CHOCOLATE.get(), 100)
                            .output(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 500),
            CooledCondition.COOLED);

    GeneratedRecipe BERRY_ICE_CREAM = createCooled("berry_ice_cream", b ->
                    b.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 500)
                            .require(LuncheonFluids.BERRY_EXTRACT.get(), 100)
                            .output(LuncheonFluids.BERRY_ICE_CREAM.get(), 500),
            CooledCondition.COOLED);

    GeneratedRecipe POWDERED_SNOW = createCooled("powdered_snow", b ->
                    b.require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .output(LuncheonFluids.POWDERED_SNOW.get(), 1000),
            CooledCondition.COOLED);

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MIXING; }
}
