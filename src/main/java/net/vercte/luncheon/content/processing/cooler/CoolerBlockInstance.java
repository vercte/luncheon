package net.vercte.luncheon.content.processing.cooler;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlock;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;

import static com.jozufozu.flywheel.core.model.ModelUtil.rotateToFace;

public class CoolerBlockInstance extends SingleRotatingInstance<CoolerBlockEntity> implements DynamicInstance {
    protected RotatingData blade;
    protected CoolerBlockEntity cooler;

    public CoolerBlockInstance(MaterialManager materialManager, CoolerBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        blade = setup(getBlade().createInstance());

        cooler = blockEntity;
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, blade);
    }

    @Override
    public void remove() {
        super.remove();
        blade.delete();
    }

    @Override
    public void beginFrame() {
        float speed = blockEntity.getBladeRotationSpeed();

        blade.setPosition(getInstancePosition())
                .nudge(0, (float) 4/16, 0)
                .setRotationalSpeed(speed);
    }


    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(LuncheonPartialModels.SHAFT_TINY, blockState, Direction.DOWN);
    }

    protected Instancer<RotatingData> getBlade() {
        return materialManager.defaultCutout()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(LuncheonPartialModels.MECHANICAL_COOLER_BLADE, blockState);
    }
}
