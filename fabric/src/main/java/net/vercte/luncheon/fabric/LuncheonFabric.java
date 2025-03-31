package net.vercte.luncheon.fabric;

import net.fabricmc.api.ModInitializer;
import net.vercte.luncheon.Luncheon;

public class LuncheonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Luncheon.initialize();
    }
}
