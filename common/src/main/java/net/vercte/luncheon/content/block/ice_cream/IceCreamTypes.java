package net.vercte.luncheon.content.block.ice_cream;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.foundation.utility.LuncheonLang;

import java.util.Locale;
import java.util.function.Function;

public enum IceCreamTypes {
    PLAIN(IceCreamPaletteBlockPattern.VANILLA_RANGE, r -> LuncheonBlocks.PLAIN_ICE_CREAM_BLOCK::get),
    CHOCOLATE(IceCreamPaletteBlockPattern.VANILLA_RANGE, r -> LuncheonBlocks.CHOCOLATE_ICE_CREAM_BLOCK::get),
    BERRY(IceCreamPaletteBlockPattern.VANILLA_RANGE, r -> LuncheonBlocks.BERRY_ICE_CREAM_BLOCK::get);

    private Function<CreateRegistrate, NonNullSupplier<Block>> factory;
    private IceCreamPalettesVariantEntry variants;

    public NonNullSupplier<Block> baseBlock;
    public IceCreamPaletteBlockPattern[] variantTypes;
    public TagKey<Item> materialTag;

    IceCreamTypes(IceCreamPaletteBlockPattern[] variantTypes,
                          Function<CreateRegistrate, NonNullSupplier<Block>> factory) {
        this.factory = factory;
        this.variantTypes = variantTypes;
    }

    public NonNullSupplier<Block> getBaseBlock() {
        return baseBlock;
    }

    public IceCreamPalettesVariantEntry getVariants() {
        return variants;
    }

    public static void register(CreateRegistrate registrate) {
        for (IceCreamTypes iceCreamVariant : values()) {
            NonNullSupplier<Block> baseBlock = iceCreamVariant.factory.apply(registrate);
            iceCreamVariant.baseBlock = baseBlock;
            String id = LuncheonLang.asId(iceCreamVariant.name() + "_ice_cream");
            iceCreamVariant.materialTag =
                    AllTags.optionalTag(BuiltInRegistries.ITEM, Luncheon.asResource("ice_cream/" + iceCreamVariant.name().toLowerCase(Locale.ROOT)));
            iceCreamVariant.variants = new IceCreamPalettesVariantEntry(id, iceCreamVariant);
        }
    }
}
