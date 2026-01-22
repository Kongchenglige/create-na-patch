package org.antarcticgardens.newage.compat.emi;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.emi.EmiSequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import static com.simibubi.create.compat.emi.CreateEmiAnimations.*;

public class EmiEnergisingSubcategory extends EmiSequencedAssemblySubCategory {
    public EmiEnergisingSubcategory() {
        super(25);
    }

    @Override
    public void addWidgets(WidgetHolder widgets, int x, int y, SequencedRecipe<?> recipe, int index) {
        widgets.addDrawable(x, y, getWidth(), 96, (graphics, mouseX, mouseY, delta) -> {
            PoseStack matrices = graphics.pose();
            matrices.translate(3, 54, 0);
            matrices.scale(.75f, .75f, .75f);

            matrices.translate(0, 0, 200);
            matrices.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-15.5f));
            matrices.mulPose(com.mojang.math.Axis.YP.rotationDegrees(22.5f));

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

        }).tooltip(getTooltip(recipe, index));

        widgets.addText(Component.literal(StringFormattingTool.formatLong(((EnergisingRecipe)recipe.getRecipe()).energyNeeded) + " ⚡"),
                x, 20, 0x1166ff, false);
    }
}
