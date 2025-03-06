package net.vercte.luncheon;

import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;
import net.vercte.luncheon.foundation.ponder.LuncheonPonderPlugin;

public class LuncheonClient {
    public static void clientInit(FMLClientSetupEvent event) {
        LuncheonPartialModels.init();

        PonderIndex.addPlugin(new LuncheonPonderPlugin());
    }
}
