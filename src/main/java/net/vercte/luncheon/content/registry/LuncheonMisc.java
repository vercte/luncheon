package net.vercte.luncheon.content.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

public class LuncheonMisc {
    public static final ForgeTier BAGUETTE_TIER = new ForgeTier(
            0,
            32,
            2.0F,
            0.0F,
            0,
            Tags.Blocks.NEEDS_WOOD_TOOL,
            () -> Ingredient.of(Items.BREAD)
    );
}
