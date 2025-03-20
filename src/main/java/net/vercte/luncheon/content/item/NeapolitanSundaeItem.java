package net.vercte.luncheon.content.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vercte.luncheon.foundation.data.LuncheonAdvancements;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class NeapolitanSundaeItem extends BottleFoodItem {
    public NeapolitanSundaeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(entity instanceof Player player) {
            LuncheonAdvancements.NEAPOLITAN_SUNDAE.awardTo(player);
        }
        return super.finishUsingItem(stack, level, entity);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_EAT;
    }
}
