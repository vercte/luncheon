package net.vercte.luncheon;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.content.mechanical_cooler.MechanicalCoolerBlockEntity;
import net.vercte.luncheon.content.mechanical_cooler.MechanicalCoolerRenderer;
import net.vercte.luncheon.content.mechanical_cooler.MechanicalCoolerVisual;

public class LuncheonBlockEntities {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<MechanicalCoolerBlockEntity> MECHANICAL_COOLER = REGISTRATE
            .blockEntity("mechanical_cooler", MechanicalCoolerBlockEntity::new)
            .visual(() -> MechanicalCoolerVisual::new)
            .renderer(() -> MechanicalCoolerRenderer::new)
            .validBlocks(LuncheonBlocks.MECHANICAL_COOLER)
            .register();

    public static void initalize() {}
}
