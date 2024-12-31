package net.vercte.luncheon.foundation.utility.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.content.processing.recipe.CooledCondition;
import net.vercte.luncheon.content.registry.LuncheonFluids;
import vectorwing.farmersdelight.common.tag.ForgeTags;

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
                    .require(Items.SNOWBALL).require(Items.SNOWBALL)
                    .output(LuncheonFluids.ICE_CREAM.get(), 250),
    CooledCondition.COOLED);

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MIXING; }
}
