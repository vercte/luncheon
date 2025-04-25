package net.vercte.luncheon.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.CoolerBlockEntity;
import net.vercte.luncheon.content.processing.cooler.CoolerBlockVisual;
import net.vercte.luncheon.content.processing.cooler.CoolerBlockRenderer;
import net.vercte.luncheon.registry.custom.LuncheonRegistrate;

public class LuncheonBlockEntityTypes {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<CoolerBlockEntity> MECHANICAL_COOLER = REGISTRATE.blockEntity("mechanical_cooler", CoolerBlockEntity::new)
            .visual(() -> CoolerBlockVisual::new)
            .validBlocks(LuncheonBlocks.MECHANICAL_COOLER)
            .renderer(() -> CoolerBlockRenderer::new)
            .register();

    public static void register() {}
}
