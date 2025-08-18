package net.vercte.luncheon.content.processing.cooler;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.vercte.luncheon.registry.LuncheonPartialModels;
import org.jetbrains.annotations.NotNull;

public class MechanicalCoolerBlockRenderer extends KineticBlockEntityRenderer<MechanicalCoolerBlockEntity> {
    public MechanicalCoolerBlockRenderer(BlockEntityRendererProvider.Context context) { super(context); }

    @Override
    public boolean shouldRenderOffScreen(@NotNull MechanicalCoolerBlockEntity be) {
        return true;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void renderSafe(MechanicalCoolerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderWater(be, partialTicks, ms, buffer, light);

        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer superBuffer = CachedBuffers.partialFacing(LuncheonPartialModels.SHAFT_TINY, blockState, Direction.DOWN);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);

        float speed = be.getFanRotationSpeed();
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        VertexConsumer vbCutout = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer bladeRender = CachedBuffers.partial(LuncheonPartialModels.SHAFT_FAN, blockState);
        bladeRender.useLevelLight(be.getLevel());
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

        FluidTank tank = be.tankInventory;
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
