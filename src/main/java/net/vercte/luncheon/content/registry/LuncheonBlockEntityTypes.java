package net.vercte.luncheon.content.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlockEntity;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerRenderer;
import net.vercte.luncheon.content.kinetics.stirrer.StirrerInstance;

public class LuncheonBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntityEntry<MechanicalStirrerBlockEntity> MECHANICAL_STIRRER = REGISTRATE
            .blockEntity("mechanical_stirrer", MechanicalStirrerBlockEntity::new)
            .instance(() -> StirrerInstance::new)
            .validBlocks(LuncheonBlocks.MECHANICAL_STIRRER)
            .renderer(() -> MechanicalStirrerRenderer::new)
            .register();

    public static void register() {}
}
