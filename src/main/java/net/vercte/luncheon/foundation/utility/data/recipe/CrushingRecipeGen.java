package net.vercte.luncheon.foundation.utility.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.content.registry.LuncheonItems;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class CrushingRecipeGen extends LuncheonProcessingRecipeGen {
    public CrushingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe GLASS_SHARDS = create("glass_shards", b -> b.duration(100)
            .require(Tags.Items.GLASS)
            .output(LuncheonItems.GLASS_SHARDS, 2)
            .output(0.5f, LuncheonItems.GLASS_SHARDS, 1));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.CRUSHING; }

    public static class MillingRecipeGen extends LuncheonProcessingRecipeGen {
        public MillingRecipeGen(PackOutput generator) { super(generator); }

        GeneratedRecipe SNOW_FROM_ICE_CUBE = create("snow_from_ice_cube", b -> b.require(LuncheonItems.ICE_CUBE)
                .output(Items.SNOWBALL)
                .output(0.5f, Items.SNOWBALL, 1));

        @Override
        protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MILLING; }
    }
}
