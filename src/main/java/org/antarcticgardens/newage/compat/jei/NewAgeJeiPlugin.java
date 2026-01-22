package org.antarcticgardens.newage.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.antarcticgardens.newage.CreateNewAge;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;

import static org.antarcticgardens.newage.CreateNewAge.MOD_ID;

@JeiPlugin
public class NewAgeJeiPlugin implements IModPlugin {
    private EnergisingJeiCategory energising;
    public static RecipeType<EnergisingRecipe> energisingType = new RecipeType<>(new ResourceLocation(MOD_ID, "energising"), EnergisingRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CreateNewAge.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        energising = new EnergisingJeiCategory();
        registration.addRecipeCategories(
                energising
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addRecipes(energisingType,
                Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(CreateNewAge.ENERGISING_RECIPE_TYPE.getType()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(NewAgeBlocks.ENERGISER_T1.asStack(), energisingType);
        registration.addRecipeCatalyst(NewAgeBlocks.ENERGISER_T2.asStack(), energisingType);
        registration.addRecipeCatalyst(NewAgeBlocks.ENERGISER_T3.asStack(), energisingType);


    }
}
