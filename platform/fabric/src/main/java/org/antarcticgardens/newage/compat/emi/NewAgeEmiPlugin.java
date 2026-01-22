package org.antarcticgardens.newage.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.antarcticgardens.newage.CreateNewAge;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;

public class NewAgeEmiPlugin implements EmiPlugin {

    public static final ResourceLocation ENERGISING_SPRITE_SHEET = new ResourceLocation(CreateNewAge.MOD_ID, "textures/gui/emi_simplified_textures.png");
    public static final EmiStack ENERGISING_WORKSTATION = EmiStack.of(NewAgeBlocks.ENERGISER_T1.asItem().getDefaultInstance());
    public static final EmiStack ENERGISING_WORKSTATION_2 = EmiStack.of(NewAgeBlocks.ENERGISER_T2.asItem().getDefaultInstance());

    public static final EmiStack ENERGISING_WORKSTATION_3 = EmiStack.of(NewAgeBlocks.ENERGISER_T3.asItem().getDefaultInstance());

    public static final EmiRecipeCategory ENERGISING
            = new EmiRecipeCategory(new ResourceLocation(CreateNewAge.MOD_ID, "energising"), ENERGISING_WORKSTATION, new EmiTexture(ENERGISING_SPRITE_SHEET, 0, 0, 16, 16));
    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(ENERGISING);
        registry.addWorkstation(ENERGISING, ENERGISING_WORKSTATION);
        registry.addWorkstation(ENERGISING, ENERGISING_WORKSTATION_2);
        registry.addWorkstation(ENERGISING, ENERGISING_WORKSTATION_3);



        RecipeManager manager = registry.getRecipeManager();

        // Use vanilla's concept of your recipes and pass them to your EmiRecipe representation
        for (Recipe<Container> recipe : manager.getAllRecipesFor(EnergisingRecipe.type.getType())) {
            registry.addRecipe(new EnergisingEmiRecipe((EnergisingRecipe) recipe));
        }
    }
}
