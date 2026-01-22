package org.antarcticgardens.newage.content.generation.carbonbrushes;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CarbonBrushesRenderer extends KineticBlockEntityRenderer<CarbonBrushesBlockEntity> {
    public CarbonBrushesRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected BlockState getRenderedBlockState(CarbonBrushesBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
