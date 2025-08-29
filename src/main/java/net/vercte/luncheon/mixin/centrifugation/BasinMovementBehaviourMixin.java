package net.vercte.luncheon.mixin.centrifugation;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.processing.basin.BasinMovementBehaviour;
import net.vercte.luncheon.content.processing.centrifugation.BasinCentrifugationMovementBehaviour;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasinMovementBehaviour.class)
@Debug(export = true)
public class BasinMovementBehaviourMixin {
    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    public void tick(MovementContext context, CallbackInfo ci) {
        BasinCentrifugationMovementBehaviour.tick(context);
    }
}
