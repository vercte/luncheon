package net.vercte.luncheon;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;
import net.vercte.luncheon.foundation.ponder.LuncheonPonderIndex;

public class LuncheonClient {
    public static void clientInit(FMLClientSetupEvent event) {
        LuncheonPartialModels.init();

        LuncheonPonderIndex.register();
        LuncheonPonderIndex.registerTags();
    }
}
