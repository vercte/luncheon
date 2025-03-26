package net.vercte.luncheon.content.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class IceCubeItem extends NotActuallyAFoodItem {
    public IceCubeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return super.finishUsingItem(stack, level, entity);
    }
}
