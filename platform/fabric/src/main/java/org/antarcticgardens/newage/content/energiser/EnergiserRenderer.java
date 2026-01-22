package org.antarcticgardens.newage.content.energiser;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EnergiserRenderer extends KineticBlockEntityRenderer<EnergiserBlockEntity> {
    public EnergiserRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(EnergiserBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (be.size > 0f && be.getLevel() != null) {
            var consumer = buffer.getBuffer(RenderType.lightning());
            float scaler = (1-be.size * 0.12f) * 0.5f;
            var buf = CachedBufferer.block(Blocks.WHITE_CONCRETE.defaultBlockState());

            buf.color(100, 150, 200, 200)
                    .translate(scaler, -1.2, scaler)
                    .scale(be.size * 0.12f, 1.3f, be.size * 0.12f)
                    .renderInto(ms, consumer);
        }
    }

    @Override
    protected BlockState getRenderedBlockState(EnergiserBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
