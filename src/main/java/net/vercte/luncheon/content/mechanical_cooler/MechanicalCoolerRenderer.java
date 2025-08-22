package net.vercte.luncheon.content.mechanical_cooler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.platform.ForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.vercte.luncheon.client.LuncheonPartialModels;

public class MechanicalCoolerRenderer extends KineticBlockEntityRenderer<MechanicalCoolerBlockEntity> {
    public MechanicalCoolerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(MechanicalCoolerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        BlockState state = be.getBlockState();
        if(level == null) return;

        renderWater(be, partialTicks, ms, buffer, light);

        if(VisualizationManager.supportsVisualization(level)) return;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer superBuffer = CachedBuffers.partialFacing(LuncheonPartialModels.SHAFT_TINY, state, Direction.DOWN);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);

        float speed = be.getFanRotationSpeed();
        float time = AnimationTickHolder.getRenderTime(level);
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        VertexConsumer vbCutout = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer bladeRender = CachedBuffers.partial(LuncheonPartialModels.SHAFT_FAN, state);
        bladeRender.useLevelLight(level);
        bladeRender.rotateCentered(angle, Direction.UP)
                .translate(0, (float) 2 / 16, 0)
                .renderInto(ms, vbCutout);
    }

    protected void renderWater(MechanicalCoolerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light) {
        LerpedFloat fluidLevel = be.getFluidLevel();
        if (fluidLevel == null)
            return;

        float minPuddleHeight = 1 / 16f;
        float totalHeight = 3 / 16f - minPuddleHeight;

        float level = fluidLevel.getValue(partialTicks);
        if (level < 1 / (512f * totalHeight))
            return;
        float clampedLevel = Mth.clamp(level * totalHeight, 0, totalHeight);

        FluidTank tank = be.getTank();
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack.isEmpty())
            return;

        float clip = 1 / 128f;

        float xMin = 1 / 16f;
        float xMax = xMin + 14 / 16f;

        float zMin = 1 / 16f;
        float zMax = xMin + 14 / 16f;

        float yMin = totalHeight + 2 / 16f + minPuddleHeight - clampedLevel;
        float yMax = yMin + clampedLevel;

        ms.pushPose();
        ms.translate(0, clampedLevel - totalHeight, 0);
        ForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
                xMin + clip, yMin, zMin + clip,
                xMax - clip, yMax, zMax - clip,
                buffer, ms, light, false, false);
        ms.popPose();
    }
}
