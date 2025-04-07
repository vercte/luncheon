package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonTags;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
@SuppressWarnings({"unused"})
public class LuncheonCrushingRecipeGen extends LuncheonProcessingRecipeGen {
    public LuncheonCrushingRecipeGen(PackOutput generator) { super(generator); }

    GeneratedRecipe GLASS_SHARDS = create("glass_shards", b -> b.duration(100)
            .require(LuncheonTags.ItemTags.GLASS.tag)
            .output(LuncheonItems.GLASS_SHARDS, 2)
            .duration(200)
            .output(0.5f, LuncheonItems.GLASS_SHARDS, 1));

    @Override
    protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.CRUSHING; }

    public static class MillingRecipeGen extends LuncheonProcessingRecipeGen {
        public MillingRecipeGen(PackOutput generator) { super(generator); }

        GeneratedRecipe SNOW_FROM_ICE_CUBE = create("snow_from_ice_cube", b -> b.require(LuncheonItems.ICE_CUBE)
                .duration(50)
                .output(0.25f, Items.SNOWBALL, 1)
                .output(0.50f, Items.SNOWBALL, 1));

        @Override
        protected IRecipeTypeInfo getRecipeType() { return AllRecipeTypes.MILLING; }
    }
}
