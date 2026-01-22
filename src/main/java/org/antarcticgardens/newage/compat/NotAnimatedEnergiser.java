package org.antarcticgardens.newage.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.AllBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import org.antarcticgardens.newage.NewAgeBlocks;

public class NotAnimatedEnergiser extends AnimatedKinetics {

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        blockElement(shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(20)
                .render(graphics);

        blockElement(NewAgeBlocks.ENERGISER_T1.getDefaultState())
                .scale(20)
                .render(graphics);

        blockElement(AllBlocks.DEPOT.getDefaultState())
                .scale(20)
                .atLocal(0, 2, 0)
                .render(graphics);

        matrixStack.popPose();
    }
}
