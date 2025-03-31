package net.vercte.luncheon.foundation.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.vercte.luncheon.content.registry.LuncheonBlocks;

public class LuncheonPonderIndex {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(LuncheonBlocks.MECHANICAL_COOLER)
                .addStoryBoard("mechanical_cooler", MechanicalCoolerScene::scene, AllCreatePonderTags.KINETIC_APPLIANCES);
    }
}
