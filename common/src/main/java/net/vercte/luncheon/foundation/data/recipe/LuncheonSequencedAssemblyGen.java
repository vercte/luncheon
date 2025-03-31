package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonFluids;
import net.vercte.luncheon.content.registry.LuncheonItems;

import java.util.function.UnaryOperator;

public class LuncheonSequencedAssemblyGen extends LuncheonProcessingRecipeGen {
    public LuncheonSequencedAssemblyGen(PackOutput output) {
        super(output);
    }

    GeneratedRecipe NEAPOLITAN_SUNDAE = sequenced("neapolitan_sundae", b -> b.require(Items.GLASS_BOTTLE)
            .transitionTo(LuncheonItems.INCOMPLETE_NEAPOLITAN_SUNDAE)
            .addOutput(LuncheonItems.NEAPOLITAN_SUNDAE, 1)
            .loops(1)
            .addStep(FillingRecipe::new, rb -> rb.require(LuncheonFluids.PLAIN_ICE_CREAM.get(), 250))
            .addStep(FillingRecipe::new, rb -> rb.require(LuncheonFluids.CHOCOLATE_ICE_CREAM.get(), 250))
            .addStep(FillingRecipe::new, rb -> rb.require(LuncheonFluids.BERRY_ICE_CREAM.get(), 250))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.SWEET_BERRIES)));

    protected GeneratedRecipe sequenced(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(Luncheon.asResource(name)))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SEQUENCED_ASSEMBLY;
    }
}
