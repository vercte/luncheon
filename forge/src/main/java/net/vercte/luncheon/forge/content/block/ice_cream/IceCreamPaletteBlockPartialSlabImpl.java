package net.vercte.luncheon.forge.content.block.ice_cream;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.vercte.luncheon.content.block.ice_cream.IceCreamPaletteBlockPartial;
import net.vercte.luncheon.content.block.ice_cream.IceCreamPaletteBlockPattern;

import java.util.function.Supplier;

public abstract class IceCreamPaletteBlockPartialSlabImpl extends IceCreamPaletteBlockPartial.Slab {
    public IceCreamPaletteBlockPartialSlabImpl(boolean customSide) {
        super(customSide);
    }

    protected void generateBlockState(DataGenContext<Block, SlabBlock> ctx, RegistrateBlockstateProvider prov,
                                      String variantName, IceCreamPaletteBlockPattern pattern, Supplier<? extends Block> block) {
        String name = ctx.getName();
        ResourceLocation mainTexture = getTexture(variantName, pattern, 0);
        ResourceLocation sideTexture = customSide ? getTexture(variantName, pattern, 1) : mainTexture;

        ModelFile bottom = prov.models()
                .slab(name, sideTexture, mainTexture, mainTexture);
        ModelFile top = prov.models()
                .slabTop(name + "_top", sideTexture, mainTexture, mainTexture);
        ModelFile doubleSlab;

        if (customSide) {
            doubleSlab = prov.models()
                    .cubeColumn(name + "_double", sideTexture, mainTexture);
        } else {
            doubleSlab = prov.models()
                    .getExistingFile(prov.modLoc(pattern.createName(variantName)));
        }

        prov.slabBlock(ctx.get(), bottom, top, doubleSlab);
    }
}
