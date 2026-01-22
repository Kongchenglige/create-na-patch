package org.antarcticgardens.newage.content.heat.stirlingengine;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class StirlingEngineRenderer extends KineticBlockEntityRenderer<StirlingEngineBlockEntity> {

    public StirlingEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(StirlingEngineBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        BlockState blockState = AllBlocks.FLYWHEEL.getDefaultState().setValue(BlockStateProperties.AXIS,
                be.getBlockState().getValue(BlockStateProperties.AXIS)
                );

        float speed = be.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = be.angle + speed * partialTicks;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderFlywheel(be, ms, light, blockState, angle, vb);
    }

    private void renderFlywheel(StirlingEngineBlockEntity be, PoseStack ms, int light, BlockState blockState, float angle,
                                VertexConsumer vb) {
        SuperByteBuffer wheel = CachedBufferer.block(blockState).centre().scale(0.2f, 0.2f, 0.2f).unCentre();
        kineticRotationTransform(wheel, be, getRotationAxisOf(be), AngleHelper.rad(angle), light);
        wheel.renderInto(ms, vb);
    }

    @Override
    protected BlockState getRenderedBlockState(StirlingEngineBlockEntity be) {
        return AllBlocks.SHAFT.getDefaultState().setValue(BlockStateProperties.AXIS,
                be.getBlockState().getValue(BlockStateProperties.AXIS)
        );
    }
}
