package net.vercte.luncheon.content.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.misc.SpicyEffect;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;

public class LuncheonMobEffects {
    private static final LuncheonRegistrate REGISTRATE = Luncheon.registrate();

    public static final RegistryEntry<SpicyEffect> SPICY = REGISTRATE.mobEffect("spicy", SpicyEffect::new)
            .lang("Spicy")
            .register();

    public static void register() {}
}
