package net.vercte.luncheon.content.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlockEntity;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlockVisual;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlockRenderer;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonBlockEntityTypes {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<MechanicalCoolerBlockEntity> MECHANICAL_COOLER = REGISTRATE.blockEntity("mechanical_cooler", MechanicalCoolerBlockEntity::new)
            .visual(() -> MechanicalCoolerBlockVisual::new)
            .validBlocks(LuncheonBlocks.MECHANICAL_COOLER)
            .renderer(() -> MechanicalCoolerBlockRenderer::new)
            .register();

    public static void register() {}
}
