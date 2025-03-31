package net.vercte.luncheon.forge;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.LuncheonClient;
import net.vercte.luncheon.content.misc.LuncheonCriteriaTriggers;
import net.vercte.luncheon.foundation.data.LuncheonAdvancements;
import net.vercte.luncheon.foundation.data.LuncheonDatagen;

@Mod(Luncheon.ID)
public class LuncheonForge {
    public LuncheonForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Luncheon.initialize();
        Luncheon.REGISTRATE.get().registerEventListeners(modEventBus);

        modEventBus.addListener(LuncheonForge::init);
        modEventBus.addListener(e -> LuncheonClient.initialize());
        modEventBus.addListener(EventPriority.LOWEST, LuncheonDatagen::gatherData);
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LuncheonAdvancements.init();
            LuncheonCriteriaTriggers.register();
        });
    }
}
