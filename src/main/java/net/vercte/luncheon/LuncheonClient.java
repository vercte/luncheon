package net.vercte.luncheon;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;

public class LuncheonClient {
    public static void clientInit(FMLClientSetupEvent event) {
        LuncheonPartialModels.init();
    }
}
