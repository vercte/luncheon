package net.vercte.luncheon.foundation.utility;

import net.createmod.catnip.lang.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

@Deprecated
public class LuncheonDebug {
    public static void actionbarMessage(String message, int color) {
        if(Minecraft.getInstance().player != null)
            Lang.builder("luncheon").add(Component.literal(message))
                    .color(color)
                    .sendStatus(Minecraft.getInstance().player);
    }

    public static void actionbarMessage(String message) {
        actionbarMessage(message, 0x00c81b);
    }
}
