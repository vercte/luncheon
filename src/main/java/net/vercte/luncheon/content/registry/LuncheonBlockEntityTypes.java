package net.vercte.luncheon.content.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlockEntity;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerRenderer;
import net.vercte.luncheon.content.kinetics.stirrer.StirrerInstance;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonBlockEntityTypes {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<MechanicalStirrerBlockEntity> MECHANICAL_STIRRER = REGISTRATE
            .blockEntity("mechanical_stirrer", MechanicalStirrerBlockEntity::new)
            .instance(() -> StirrerInstance::new)
            .validBlocks(LuncheonBlocks.MECHANICAL_STIRRER)
            .renderer(() -> MechanicalStirrerRenderer::new)
            .register();

    public static void register() {}
}
