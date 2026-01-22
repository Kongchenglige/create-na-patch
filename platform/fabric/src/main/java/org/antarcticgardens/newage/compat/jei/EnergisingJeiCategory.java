package org.antarcticgardens.newage.compat.jei;

import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.compat.RenderingUtil;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import java.util.Arrays;
import java.util.Collections;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getRenderedSlot;

public class EnergisingJeiCategory implements IRecipeCategory<EnergisingRecipe> {

    @Override
    public RecipeType<EnergisingRecipe> getRecipeType() {
        return NewAgeJeiPlugin.energisingType;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.category.create_new_age.energising");
    }

    @Override
    public IDrawable getBackground() {
        return new IDrawable() {
            @Override
            public int getWidth() {
                return 130;
            }

            @Override
            public int getHeight() {
                return 25;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {

            }
        };
    }

    @Override
    public IDrawable getIcon() {
        return new ItemIcon(NewAgeBlocks.ENERGISER_T2::asStack);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EnergisingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 4)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStacks(Arrays.asList(recipe.getIngredients().get(0).getItems()));

        int i = 0;
        for (ProcessingOutput itemStacks : recipe.getRollableResults()) {
            int xPos = 80 + (i % 5) * 19;
            int yPos = 4 + (i / 5) * -19;

            builder
                    .addSlot(RecipeIngredientRole.OUTPUT, xPos, yPos)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStacks(Collections.singletonList(itemStacks.getStack()));
            i++;
        }
    }

    @Override
    public void draw(EnergisingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 31, 8);
        RenderingUtil.drawCenteredStringWithoutShadow(guiGraphics, Minecraft.getInstance().font, Component.literal(StringFormattingTool.formatLong(recipe.energyNeeded) + " ⚡"), 50, 18, 0x1166ff);
    }
}
