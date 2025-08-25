package net.vercte.luncheon.content.processing.centrifugation;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.bearing.BearingContraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.vercte.luncheon.LuncheonConfig;
import net.vercte.luncheon.foundation.utility.LuncheonDebug;

public class BasinCentrifugationMovementBehaviour {
    private static final String PROGRESS_TAG = "luncheon$centrifuge_progress";

    public static void tick(MovementContext context) {
        if(context.world.isClientSide()) return;
        Contraption contraption = context.contraption;
        if(contraption instanceof BearingContraption bearing) {
            if(bearing.getFacing() != Direction.UP) return;

            double speed = Math.min(context.motion.length(), LuncheonConfig.maxCentrifugeSpeed);

            int newProgress = context.data.getInt(PROGRESS_TAG);
            if(speed > LuncheonConfig.minCentrifugeSpeed) {
                newProgress += (int) Math.round(speed * 2);
                if(Minecraft.getInstance().player != null)
                    LuncheonDebug.actionbarMessage(String.format("speed %.2f, progress %d", speed, newProgress));
            }

            context.data.putInt(PROGRESS_TAG, newProgress);
        }
    }
}
