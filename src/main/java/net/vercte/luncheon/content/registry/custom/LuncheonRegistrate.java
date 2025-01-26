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

    public <T extends Block> BlockBuilder<T, CreateRegistrate> iceCreamBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, NonNullSupplier<Block> propertiesFrom) {
        BlockBuilder<T, CreateRegistrate> builder = super.block(name, factory).initialProperties(propertiesFrom)
                .blockstate((c, p) -> {
                    final String location = "block/palettes/ice_cream/" + c.getName();
                    p.simpleBlock(c.get(), p.models()
                            .cubeAll(c.getName(), p.modLoc(location)));
                })
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item()
                .model((c, p) -> p.cubeAll(c.getName(),
                        p.modLoc("block/palettes/ice_cream/" + c.getName())))
                .build();
        return builder;
    }

    public BlockBuilder<Block, CreateRegistrate> iceCreamBlock(String name, NonNullSupplier<Block> propertiesFrom) {
        return iceCreamBlock(name, Block::new, propertiesFrom);
    }
}
