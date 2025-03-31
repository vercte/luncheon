package net.vercte.luncheon.content.registry.custom;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class LuncheonRegistrate extends CreateRegistrate {
    protected LuncheonRegistrate(String modid) {
        super(modid);
    }

    public static LuncheonRegistrate create(String modid) {
        return new LuncheonRegistrate(modid);
    }

    public <T extends MobEffect> MobEffectBuilder<T, LuncheonRegistrate> mobEffect(String name, Supplier<? extends T> factory) {
        return mobEffect(this, name, factory);
    }

    public <T extends MobEffect, P> MobEffectBuilder<T, P> mobEffect(P parent, Supplier<? extends T> factory) {
        return mobEffect(parent, currentName(), factory);
    }

    public <T extends MobEffect, P> MobEffectBuilder<T, P> mobEffect(P parent, String name, Supplier<? extends T> factory) {
        return entry(name, callback -> new MobEffectBuilder<>(this, parent, name, callback, factory));
    }
}
