package org.antarcticgardens.newage.tools;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.antarcticgardens.newage.CreateNewAge;

import static org.antarcticgardens.newage.CreateNewAge.MOD_ID;

public class RecipeTool {
    public static <S extends RecipeSerializer<?>> IRecipeTypeInfo createIRecipeTypeInfo(String name, S serializer) {
        RecipeType<?> type = RecipeType.register(name);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(MOD_ID, name), serializer);

        return new IRecipeTypeInfo() {
            @Override
            public ResourceLocation getId() {
                return new ResourceLocation(CreateNewAge.MOD_ID, name);
            }

            @Override
            public <T extends RecipeSerializer<?>> T getSerializer() {
                return (T) serializer;
            }

            @Override
            public <T extends RecipeType<?>> T getType() {
                return (T) type;
            }
        };
    }
}
