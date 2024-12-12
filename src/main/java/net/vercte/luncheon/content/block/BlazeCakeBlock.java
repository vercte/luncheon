package net.vercte.luncheon.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.vercte.luncheon.content.misc.SpicyEffect;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonMobEffects;
import org.lwjgl.system.NonnullDefault;
import vectorwing.farmersdelight.common.block.PieBlock;

@NonnullDefault
public class BlazeCakeBlock extends PieBlock {
    public BlazeCakeBlock(BlockBehaviour.Properties properties) {
        super(properties, LuncheonItems.BLAZE_CAKE_SLICE::get);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
        if (playerIn.canEat(false)) {
            playerIn.setSecondsOnFire(4);
            playerIn.addEffect(new MobEffectInstance(LuncheonMobEffects.SPICY.get(), 8 * 20));
        }

        return super.consumeBite(level, pos, state, playerIn);
    }
}
