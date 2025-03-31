package net.vercte.luncheon.foundation.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.vercte.luncheon.Luncheon;

import javax.annotation.Nonnull;

public class LuncheonPonderPlugin implements PonderPlugin {
    @Override
    public @Nonnull String getModId() {
        return Luncheon.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        LuncheonPonderIndex.register(helper);
    }
}
