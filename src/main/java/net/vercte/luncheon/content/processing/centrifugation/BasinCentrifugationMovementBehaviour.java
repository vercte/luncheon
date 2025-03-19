package net.vercte.luncheon.content.processing.centrifugation;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.bearing.BearingContraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.Direction;

public class BasinCentrifugationMovementBehaviour {
    public static void tick(MovementContext context) {
        Contraption contraption = context.contraption;
        if(contraption instanceof BearingContraption bearing) {
            if(bearing.getFacing() != Direction.UP) return;
            double speed = context.motion.length();
        }
    }
}
