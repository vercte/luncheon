package net.vercte.luncheon.content.processing.cooler;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.minecraft.core.Direction;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;

public class CoolerBlockInstance extends SingleRotatingInstance<CoolerBlockEntity> implements DynamicInstance {
    protected RotatingData fan;
    protected CoolerBlockEntity cooler;

    public CoolerBlockInstance(MaterialManager materialManager, CoolerBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        fan = setup(getFan().createInstance());

        cooler = blockEntity;
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, fan);
    }

    @Override
    public void remove() {
        super.remove();
        fan.delete();
    }

    @Override
    public void beginFrame() {
        float speed = blockEntity.getBladeRotationSpeed();

        fan.setPosition(getInstancePosition())
                .nudge(0, (float) 2/16, 0)
                .setRotationalSpeed(speed);
    }


    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(LuncheonPartialModels.SHAFT_TINY, blockState, Direction.DOWN);
    }

    protected Instancer<RotatingData> getFan() {
        return materialManager.defaultCutout()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(LuncheonPartialModels.SHAFT_FAN, blockState);
    }
}
