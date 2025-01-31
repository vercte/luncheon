package net.vercte.luncheon.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vercte.luncheon.content.misc.LuncheonDamageSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    private void finishUsingItem(Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack item = entity.getUseItem();
        if(item.getOrCreateTag().getBoolean("luncheon.spiked")) {
            entity.hurt(LuncheonDamageSources.glass_spiked(level), 3);
        }
    }
}
