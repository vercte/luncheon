package net.vercte.luncheon.content.misc;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.vercte.luncheon.content.registry.LuncheonDamageTypes;

public class LuncheonDamageSources {
    public static DamageSource glass_shards(Level level) {
        return source(LuncheonDamageTypes.GLASS_SHARDS, level);
    }

    public static DamageSource glass_spiked(Level level) {
        return source(LuncheonDamageTypes.GLASS_SPIKED, level);
    }

    public static DamageSource too_spicy(Level level) {
        return source(LuncheonDamageTypes.TOO_SPICY, level);
    }

    private static DamageSource source(ResourceKey<DamageType> key, LevelReader level) {
        Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new DamageSource(registry.getHolderOrThrow(key));
    }
}
