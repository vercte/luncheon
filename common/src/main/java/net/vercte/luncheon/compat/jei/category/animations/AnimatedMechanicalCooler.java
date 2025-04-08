package net.vercte.luncheon.compat.jei.category.animations;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.client.gui.GuiGraphics;
import net.vercte.luncheon.content.processing.cooler.MechanicalCoolerBlock;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.misc.LuncheonPartialModels;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class AnimatedMechanicalCooler extends AnimatedKinetics {
    private MechanicalCoolerBlock.CoolingLevel coolingLevel;

    public AnimatedMechanicalCooler withCooling(MechanicalCoolerBlock.CoolingLevel coolingLevel) {
        this.coolingLevel = coolingLevel;
        return this;
    }

    @Override
    public void draw(GuiGraphics graphics, int x, int y) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(x, y, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 23;

        PartialModel fan = LuncheonPartialModels.SHAFT_FAN;

        blockElement(fan).atLocal(0, 1.65 - (2f / 16), 0)
                .rotateBlock(0, getCurrentAngle()*8, 0)
                .scale(scale)
                .render(graphics);

        blockElement(LuncheonBlocks.MECHANICAL_COOLER.getDefaultState()).atLocal(0, 1.65, 0)
                .scale(scale)
                .render(graphics);

        matrixStack.scale(scale, -scale, scale);
        matrixStack.translate(0, -1.8, 0);

        matrixStack.popPose();
    }
}
