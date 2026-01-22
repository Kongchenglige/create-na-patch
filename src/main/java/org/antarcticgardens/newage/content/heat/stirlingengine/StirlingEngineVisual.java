package org.antarcticgardens.newage.content.heat.stirlingengine;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StirlingEngineVisual extends KineticBlockEntityVisual<StirlingEngineBlockEntity> implements SimpleDynamicVisual {

    protected final RotatingInstance shaft;
    protected float lastAngle = Float.NaN;

    public StirlingEngineVisual(VisualizationContext context, StirlingEngineBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        var axis = rotationAxis();
        shaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                .createInstance();

        shaft.setup(StirlingEngineVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(axis)
                .setChanged();
    }

    @Override
    public void beginFrame(Context ctx) {

        float partialTicks = ctx.partialTick();

        float speed = blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - lastAngle) < 0.001)
            return;

        lastAngle = angle;
    }

    @Override
    public void update(float pt) {
        shaft.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(pos, shaft);
    }

    @Override
    public void _delete() {
        shaft.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(shaft);
    }
}
