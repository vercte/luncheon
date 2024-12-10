package net.vercte.luncheon.content.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.vercte.luncheon.content.registry.LuncheonItems;
import vectorwing.farmersdelight.common.block.PieBlock;

public class BlazeCakeBlock extends PieBlock {
    public BlazeCakeBlock(BlockBehaviour.Properties properties) {
        super(properties, LuncheonItems.BLAZE_CAKE_SLICE::get);
    }
}
