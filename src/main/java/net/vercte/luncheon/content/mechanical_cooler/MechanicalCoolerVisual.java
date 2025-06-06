package net.vercte.luncheon.content.mechanical_cooler;

import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction;
import net.vercte.luncheon.client.LuncheonPartialModels;

public class MechanicalCoolerVisual extends SingleAxisRotatingVisual<MechanicalCoolerBlockEntity> implements SimpleDynamicVisual {
    public MechanicalCoolerVisual(VisualizationContext context, MechanicalCoolerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(LuncheonPartialModels.SHAFT_TINY, Direction.DOWN));
    }

    @Override
    public void beginFrame(Context ctx) {

    }
}
