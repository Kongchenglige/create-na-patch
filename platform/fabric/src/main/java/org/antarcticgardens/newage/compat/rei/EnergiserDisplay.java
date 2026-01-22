package org.antarcticgardens.newage.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;

import java.util.Collections;
import java.util.List;

public class EnergiserDisplay implements Display {

    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> output;

    public final EnergisingRecipe recipe;

    public EnergiserDisplay (EnergisingRecipe recipe) {
        inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        output = Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getRollableResultsAsItemStacks()));
        this.recipe = recipe;
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return NewAgeReiPlugin.identifier;
    }
}
