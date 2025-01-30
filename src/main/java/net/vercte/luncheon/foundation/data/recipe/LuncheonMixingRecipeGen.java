package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.content.registry.LuncheonFluids;

public class LuncheonMixingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonMixingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe CREAM = create("cream", b ->
        b.require(Tags.Fluids.MILK, 250)
         .output(LuncheonFluids.CREAM.get(), 250)
         .requiresHeat(HeatCondition.HEATED)
    );

    GeneratedRecipe ICE_CREAM = createCooled("ice_cream", b ->
            b.require(LuncheonFluids.CREAM.get(), 250)
                    .require(Items.SUGAR)
                    .require(LuncheonFluids.POWDERED_SNOW.get(), 500)
                    .output(LuncheonFluids.ICE_CREAM.get(), 250),
            CooledCondition.COOLED);

    GeneratedRecipe POWDERED_SNOW = createCooled("powdered_snow", b ->
                    b.require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .require(Items.SNOWBALL).require(Items.SNOWBALL)
                            .output(LuncheonFluids.POWDERED_SNOW.get(), 1000),
            CooledCondition.COOLED);

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MIXING; }
}
