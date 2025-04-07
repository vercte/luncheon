package net.vercte.luncheon.content.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class LuncheonTiers {
    public static final ForgeTier BAGUETTE_TIER = new ForgeTier(
            0,
            10,
            2.0F,
            0.0F,
            0,
            LuncheonTags.Blocks.NEEDS_WOOD_TOOL,
            () -> Ingredient.of(Items.BREAD)
    );
}
