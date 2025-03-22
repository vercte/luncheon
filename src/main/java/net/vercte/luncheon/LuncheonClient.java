package net.vercte.luncheon;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;
import net.vercte.luncheon.foundation.ponder.LuncheonPonderPlugin;

public class LuncheonClient {
    public static void clientInit(FMLClientSetupEvent event) {
        LuncheonPartialModels.init();

        ItemProperties.register(LuncheonItems.INCOMPLETE_NEAPOLITAN_SUNDAE.get(),
                Luncheon.asResource("progress"), (stack, level, living, id) -> ((SequencedAssemblyItem)stack.getItem()).getProgress(stack));
        ItemProperties.register(LuncheonItems.INCOMPLETE_NEAPOLITAN_SUNDAE.get(),
                Luncheon.asResource("inprogress"), (stack, level, living, id) -> ((SequencedAssemblyItem)stack.getItem()).getProgress(stack) > 0 ? 1.0F : 0);

        PonderIndex.addPlugin(new LuncheonPonderPlugin());
    }
}
