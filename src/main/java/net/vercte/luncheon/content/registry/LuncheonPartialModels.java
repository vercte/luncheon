package net.vercte.luncheon.content.registry;

import com.jozufozu.flywheel.core.PartialModel;
import net.vercte.luncheon.Luncheon;

public class LuncheonPartialModels {
    public static final PartialModel SHAFT_TINY = block("shaft_tiny");
    public static final PartialModel SHAFT_FAN = block("shaft_fan");

    private static PartialModel block(String path) {
        return new PartialModel(Luncheon.asResource("block/" + path));
    }

    public static void init() {}
}
