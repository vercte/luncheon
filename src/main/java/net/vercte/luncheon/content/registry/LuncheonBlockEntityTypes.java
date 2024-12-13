package net.vercte.luncheon.content.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.processing.cooler.CoolerBlockEntity;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonBlockEntityTypes {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<CoolerBlockEntity> COOLER = REGISTRATE.blockEntity("cooler", CoolerBlockEntity::new)
            .validBlocks(LuncheonBlocks.COOLER)
            .register();

    public static void register() {}
}
