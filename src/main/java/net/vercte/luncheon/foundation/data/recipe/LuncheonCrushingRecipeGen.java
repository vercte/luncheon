package net.vercte.luncheon.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import com.simibubi.create.api.data.recipe.MillingRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.vercte.luncheon.registry.LuncheonItems;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
@SuppressWarnings({"unused"})
public class LuncheonCrushingRecipeGen extends CrushingRecipeGen {
    public LuncheonCrushingRecipeGen(PackOutput generator) { super(generator, "luncheon"); }

    GeneratedRecipe GLASS_SHARDS = create("glass_shards", b -> b.duration(100)
            .require(Tags.Items.GLASS)
            .output(LuncheonItems.GLASS_SHARDS, 2)
            .duration(200)
            .output(0.5f, LuncheonItems.GLASS_SHARDS, 1));

    public static class Milling extends MillingRecipeGen {
        public Milling(PackOutput generator) { super(generator, "luncheon"); }

        GeneratedRecipe SNOW_FROM_ICE_CUBE = create("snow_from_ice_cube", b -> b.require(LuncheonItems.ICE_CUBE)
                .duration(50)
                .output(0.25f, Items.SNOWBALL, 1)
                .output(0.50f, Items.SNOWBALL, 1));

    }
}
