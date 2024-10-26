package net.vercte.luncheon.content.kinetics.stirrer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalStirrerRenderer extends KineticBlockEntityRenderer<MechanicalStirrerBlockEntity> {
    public MechanicalStirrerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(MechanicalStirrerBlockEntity be) {
        return true;
    }

    @Override
    protected void renderSafe(MechanicalStirrerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {

        if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState blockState = be.getBlockState();
        BlockPos pos = be.getBlockPos();

        VertexConsumer cogwheelBuffer = buffer.getBuffer(RenderType.solid());
        VertexConsumer shaftBuffer = buffer.getBuffer(RenderType.solid());

        SuperByteBuffer cogwheel = CachedBufferer.partial(AllPartialModels.SHAFTLESS_COGWHEEL, blockState);
        standardKineticRotationTransform(cogwheel, be, light).renderInto(ms, cogwheelBuffer);

        Axis axis = Axis.Y;
        SuperByteBuffer shaft = CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, blockState, Direction.UP);
        kineticRotationTransform(shaft, be, axis, getAngleForTe(be, pos, axis), light).renderInto(ms, shaftBuffer);

        float speed = be.getSpeed() / 2;
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        VertexConsumer vbCutout = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer headRender = CachedBufferer.partial(AllPartialModels.MECHANICAL_MIXER_HEAD, blockState);
        headRender.rotateCentered(Direction.UP, angle)
                .translate(0, -9 / 16f, 0)
                .light(light)
                .renderInto(ms, vbCutout);
    }
}
