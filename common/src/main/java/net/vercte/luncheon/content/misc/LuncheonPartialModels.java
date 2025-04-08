package net.vercte.luncheon.content.misc;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.vercte.luncheon.Luncheon;

public class LuncheonPartialModels {
    public static final PartialModel SHAFT_TINY = block("shaft_tiny");
    public static final PartialModel SHAFT_FAN = block("shaft_fan");

    private static PartialModel block(String path) {
        return PartialModel.of(Luncheon.asResource("block/" + path));
    }

    public static void init() {}
}
