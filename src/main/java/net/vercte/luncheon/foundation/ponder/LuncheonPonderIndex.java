package net.vercte.luncheon.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonBlocks;

public class LuncheonPonderIndex {
    public static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Luncheon.ID);

    public static void register() {
        HELPER.addStoryBoard(LuncheonBlocks.MECHANICAL_COOLER, "mechanical_cooler", MechanicalCoolerScene::scene);
    }

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(LuncheonBlocks.MECHANICAL_COOLER);

        PonderRegistry.TAGS.forTag(AllPonderTags.FLUIDS)
                .add(LuncheonBlocks.MECHANICAL_COOLER);
    }
}
