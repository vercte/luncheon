package net.vercte.luncheon.foundation.utility.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.vercte.luncheon.Luncheon;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.PieBlock;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class LuncheonBlockstates {
    public static ResourceLocation fdResourceBlock(String path) {
        return new ResourceLocation(FarmersDelight.MODID, "block/" + path);
    }

    public static ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(Luncheon.MODID, "block/" + path);
    }

    public static <T extends Block> void cubeAll(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                 String textureSubDir) {
        cubeAll(ctx, prov, textureSubDir, ctx.getName());
    }

    public static <T extends Block> void cubeAll(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                 String textureSubDir, String name) {
        String texturePath = "block/" + textureSubDir + name;
        prov.simpleBlock(ctx.get(), prov.models()
                .cubeAll(ctx.getName(), prov.modLoc(texturePath)));
    }

    public static <T extends PieBlock> VariantBlockStateBuilder PieBlock(DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        return p.getVariantBuilder(c.getEntry())
                .forAllStates(state -> {
                    int bites = state.getValue(PieBlock.BITES);
                    String locationPath = String.format("block/%s/bites", c.getId().getPath());
                    String location = String.join("",locationPath, String.valueOf(bites));
                    ModelFile model = p.models().getExistingFile(p.modLoc(location));
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(((int) state.getValue(PieBlock.FACING).toYRot() + 180) % 360)
                            .build();
                });
    }

    public static <T extends Block> VariantBlockStateBuilder stageBlock(DataGenContext<Block, T> c, RegistrateBlockstateProvider p, IntegerProperty ageProperty, Property<?>... ignored) {
        return p.getVariantBuilder(c.getEntry())
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = String.join("", c.getId().getPath(), "_stage", Integer.toString(ageSuffix));
                    return ConfiguredModel.builder()
                            .modelFile(p.models().cross(stageName, resourceBlock(stageName)).renderType("cutout")).build();
                }, ignored);
    }

    // now I have to deal with it too! (adapted from Farmer's Delight code BlockStates.customStageBlock for registrate)
    public static <T extends Block> VariantBlockStateBuilder HiddenStageBlock(DataGenContext<Block, T> c, RegistrateBlockstateProvider p, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        return p.getVariantBuilder(c.getEntry())
                .forAllStatesExcept(blockState -> {
                    int ageSuffix = blockState.getValue(ageProperty);
                    String stageName = String.join("", c.getId().getPath(), "_stage",
                            Integer.toString(suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix))));
                    if (parent == null) {
                        return ConfiguredModel.builder()
                                .modelFile(p.models().cross(stageName, resourceBlock(stageName)).renderType("cutout")).build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(p.models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName)).renderType("cutout")).build();
                }, ignored);
    }

    public static <T extends Block> VariantBlockStateBuilder VineCropBlock(DataGenContext<Block, T> c, RegistrateBlockstateProvider p, IntegerProperty stageProperty, BooleanProperty ropeloggedProperty) {
        return p.getVariantBuilder(c.getEntry())
                .forAllStates(blockState -> {
                    boolean ropelogged = blockState.getValue(ropeloggedProperty);
                    int stage = blockState.getValue(stageProperty);
                    String path = String.join("", c.getId().getPath(), ropelogged ? "_vine" : "", "_stage", Integer.toString(stage));
                    String ropePath = String.join("", c.getId().getPath(), "_coiled_rope");

                    if(ropelogged) {
                        ModelFile model = p.models().withExistingParent(path, fdResourceBlock("crop_with_rope"))
                                .renderType("minecraft:cutout")
                                .texture("crop", resourceBlock(path))
                                .texture("rope_side", resourceBlock(ropePath))
                                .texture("rope_top", fdResourceBlock("rope_top"));
                        return ConfiguredModel.builder()
                                .modelFile(model).build();
                    }
                    ModelFile model = p.models().singleTexture(path, fdResourceBlock("crop_cross"), "cross", resourceBlock(path))
                            .renderType("minecraft:cutout");
                    return ConfiguredModel.builder()
                            .modelFile(model).build();
                });
    }
}