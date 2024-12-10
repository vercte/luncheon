package net.vercte.luncheon.content.kinetics.stirrer;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.minecraft.core.Direction;

public class StirrerInstance extends EncasedCogInstance implements DynamicInstance {
    private final RotatingData stirrerHead;

    public StirrerInstance(MaterialManager materialManager, MechanicalStirrerBlockEntity blockEntity) {
        super(materialManager, blockEntity, false);

        this.stirrerHead = materialManager.defaultCutout()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(AllPartialModels.MECHANICAL_MIXER_HEAD, blockState)
                .createInstance();
        this.stirrerHead.setRotationAxis(Direction.Axis.Y);

        beginFrame();
    }

    @Override
    public void beginFrame() {
        this.stirrerHead.setPosition(getInstancePosition())
                .nudge(0, -9 / 16F, 0)
                .setRotationalSpeed(blockEntity.getSpeed());
    }

    @Override
    public void updateLight() {
        super.updateLight();

        relight(pos.below(), stirrerHead);
    }

    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(AllPartialModels.SHAFTLESS_COGWHEEL, blockEntity.getBlockState());
    }

    @Override
    public void remove() {
        super.remove();
        stirrerHead.delete();
    }
}
