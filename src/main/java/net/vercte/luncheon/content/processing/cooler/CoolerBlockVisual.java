package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.vercte.luncheon.content.registry.LuncheonPartialModels;

public class CoolerBlockVisual extends SingleAxisRotatingVisual<CoolerBlockEntity> implements SimpleDynamicVisual {
    protected RotatingInstance fan;
    protected CoolerBlockEntity cooler;

    public CoolerBlockVisual(VisualizationContext context, CoolerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(LuncheonPartialModels.SHAFT_TINY));

        fan = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(LuncheonPartialModels.SHAFT_FAN)).createInstance();

        cooler = blockEntity;
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(pos, fan);
    }

    @Override
    public void _delete() {
        super._delete();
        fan.delete();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        float speed = blockEntity.getBladeRotationSpeed();

        fan.setPosition(getVisualPosition())
                .nudge(0, (float) 2/16, 0)
                .setRotationalSpeed(speed);
    }
}
