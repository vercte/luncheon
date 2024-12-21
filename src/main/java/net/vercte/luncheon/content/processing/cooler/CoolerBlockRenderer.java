package net.vercte.luncheon.content.processing.cooler;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;

public class CoolerBlockRenderer extends KineticBlockEntityRenderer<CoolerBlockEntity> {
    public CoolerBlockRenderer(BlockEntityRendererProvider.Context context) { super(context); }

    @Override
    public boolean shouldRenderOffScreen(CoolerBlockEntity be) {
        return true;
    }

    @Override
    protected void renderSafe(CoolerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderWater(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer superBuffer = CachedBufferer.partialFacing(LuncheonPartialModels.SHAFT_TINY, blockState, Direction.DOWN);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);

        float speed = be.getBladeRotationSpeed();
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        VertexConsumer vbCutout = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer bladeRender = CachedBufferer.partial(LuncheonPartialModels.MECHANICAL_COOLER_BLADE, blockState);
        bladeRender.rotateCentered(Direction.UP, angle)
                .translate(0, (float) 4 / 16, 0)
                .renderInto(ms, vbCutout);
    }

    protected void renderWater(CoolerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        LerpedFloat fluidLevel = be.getFluidLevel();
        if (fluidLevel == null)
            return;

        float minPuddleHeight = 1 / 16f;
        float totalHeight = 1 / 4f - minPuddleHeight;

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

        float yMin = totalHeight + 4 / 16f + minPuddleHeight - clampedLevel;
        float yMax = yMin + clampedLevel;

        ms.pushPose();
        ms.translate(0, clampedLevel - totalHeight, 0);
        FluidRenderer.renderFluidBox(fluidStack,
                xMin + clip, yMin, zMin + clip,
                xMax - clip, yMax, zMax - clip,
                buffer, ms, light, false);
        ms.popPose();
    }
}
