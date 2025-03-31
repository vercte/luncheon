package net.vercte.luncheon.mixin.deployer;

import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(AdvancementBehaviour.class)
public interface AdvancementBehaviourAccessor {
    @Accessor
    UUID getPlayerId();
}
