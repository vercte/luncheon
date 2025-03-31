package net.vercte.luncheon.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.vercte.luncheon.LuncheonClient;

public class LuncheonFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LuncheonClient.initialize();
    }
}
