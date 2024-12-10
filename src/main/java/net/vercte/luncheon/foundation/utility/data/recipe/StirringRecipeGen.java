package net.vercte.luncheon.foundation.utility.data.recipe;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.content.registry.LuncheonRecipeTypes;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class StirringRecipeGen extends LuncheonProcessingRecipeGen {
    public StirringRecipeGen(PackOutput generator) {
        super(generator);
    }

    GeneratedRecipe OBSIDIAN = create("obsidian", b -> b.require(FluidTags.WATER, 500)
            .require(FluidTags.LAVA, 500)
            .output(Items.OBSIDIAN));

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return LuncheonRecipeTypes.STIRRING;
    }
}
