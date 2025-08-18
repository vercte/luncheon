package net.vercte.luncheon.content.mechanical_cooler;

import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction;
import net.vercte.luncheon.client.LuncheonPartialModels;

import java.util.function.Consumer;

public class MechanicalCoolerVisual extends SingleAxisRotatingVisual<MechanicalCoolerBlockEntity> implements SimpleDynamicVisual {
    private MechanicalCoolerBlockEntity cooler;

    protected RotatingInstance fan;
    private float lastSpeed;

    public MechanicalCoolerVisual(VisualizationContext context, MechanicalCoolerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(LuncheonPartialModels.SHAFT_TINY, Direction.DOWN));
        this.cooler = blockEntity;

        this.fan = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(LuncheonPartialModels.SHAFT_FAN)).createInstance();

        this.lastSpeed = this.cooler.getFanRotationSpeed();
        fan.setup(blockEntity, this.lastSpeed)
                .setPosition(getVisualPosition())
                .nudge(0, (float) 2/16, 0)
                .rotateToFace(Direction.UP, Direction.Axis.Y)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(pos, this.fan);
    }

    @Override
    public void _delete() {
        super._delete();
        this.fan.delete();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) { this.animateFan(); }

    public void animateFan() {
        float fanSpeed = this.cooler.getFanRotationSpeed();

        if(fanSpeed == this.lastSpeed) return;
        this.lastSpeed = fanSpeed;
        this.fan.setRotationalSpeed(fanSpeed)
                .setChanged();
    }

    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(this.fan);
    }
}
