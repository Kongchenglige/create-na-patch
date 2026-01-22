package org.antarcticgardens.newage.compat.emi;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;
import org.antarcticgardens.newage.tools.StringFormattingTool;

public class EnergisingEmiRecipe extends BasicEmiRecipe {

    private final int energyNeeded;

    public EnergisingEmiRecipe(EnergisingRecipe recipe) {
        super(NewAgeEmiPlugin.ENERGISING, recipe.getId(), 140, 30);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        for (ProcessingOutput rollableResult : recipe.getRollableResults()) {
            this.outputs.add(EmiStack.of(rollableResult.getStack()));
        }
        energyNeeded = recipe.energyNeeded;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);

        widgets.addSlot(inputs.get(0), 0, 0);

        Component text = Component.literal(StringFormattingTool.formatLong(energyNeeded) + " ⚡");
        widgets.addText(text, 30 - text.getString().length(), 20, 0x1166ff, false);

        int x = 58;
        for (EmiStack output : outputs) {
            widgets.addSlot(output, x, 0).recipeContext(this);
            x+=20;
        }
    }
}
