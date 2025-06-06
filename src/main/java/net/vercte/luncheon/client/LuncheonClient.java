package net.vercte.luncheon.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuncheonClient {
    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        LuncheonPartialModels.initialize();
    }
}
