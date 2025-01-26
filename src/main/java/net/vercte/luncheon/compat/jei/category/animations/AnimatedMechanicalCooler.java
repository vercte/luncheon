package net.vercte.luncheon.compat.jei.category.animations;

import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.vercte.luncheon.content.processing.cooler.CoolerBlock;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class AnimatedMechanicalCooler extends AnimatedKinetics {
    private CoolerBlock.CoolingLevel coolingLevel;

    public AnimatedMechanicalCooler withCooling(CoolerBlock.CoolingLevel coolingLevel) {
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

        PartialModel blade = LuncheonPartialModels.MECHANICAL_COOLER_BLADE;

        blockElement(blade).atLocal(0, 1.65 - (4f / 16), 0)
                .rotateBlock(0, getCurrentAngle()*4, 0)
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
