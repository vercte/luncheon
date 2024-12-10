package net.vercte.luncheon.content.misc;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SpicyEffect extends MobEffect {
    private int ticksActive = 0;

    public SpicyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x960014);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "0f389f84-a8f3-4a1b-9d28-90d12b4865f5", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(ticksActive++ % 160 == 0) {
            entity.hurt(LuncheonDamageSources.too_spicy(entity.level()), 1);
        }
    }
}