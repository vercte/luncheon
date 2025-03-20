package net.vercte.luncheon.content.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vercte.luncheon.foundation.data.LuncheonAdvancements;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class IceCubeItem extends NotActuallyAFoodItem {
    public IceCubeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(entity instanceof Player player) {
            LuncheonAdvancements.ICE_CUBE.awardTo(player);
        }
        return super.finishUsingItem(stack, level, entity);
    }
}
