package net.vercte.luncheon.content.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vercte.luncheon.LuncheonConfig;
import net.vercte.luncheon.content.misc.LuncheonDamageSources;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class GlassShardsItem extends NotActuallyAFoodItem {
    public GlassShardsItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.hurt(LuncheonDamageSources.glass_shards(level), LuncheonConfig.glassShardDamage);
        super.finishUsingItem(stack, level, entity);
        return stack;
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }
}
