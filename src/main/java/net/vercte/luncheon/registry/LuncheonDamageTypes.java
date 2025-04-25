package net.vercte.luncheon.registry;

import com.simibubi.create.foundation.damageTypes.DamageTypeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.vercte.luncheon.Luncheon;

public class LuncheonDamageTypes {
    public static final ResourceKey<DamageType> GLASS_SHARDS = key("glass_shards");
    public static final ResourceKey<DamageType> GLASS_SPIKED = key("glass_spiked");
    public static final ResourceKey<DamageType> TOO_SPICY = key("too_spicy");

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Luncheon.asResource(name));
    }

    public static void bootstrap(BootstapContext<DamageType> ctx) {
        new DamageTypeBuilder(GLASS_SHARDS).register(ctx);
        new DamageTypeBuilder(GLASS_SPIKED).register(ctx);
        new DamageTypeBuilder(TOO_SPICY).register(ctx);
    }
}
