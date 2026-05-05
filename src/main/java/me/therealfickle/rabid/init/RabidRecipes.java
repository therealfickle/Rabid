package me.therealfickle.rabid.init;

import me.therealfickle.rabid.item.crafting.ReconstructorRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidRecipes {

    RecipeType<ReconstructorRecipe> RECONSTRUCTING = register("reconstructing");
    RecipeSerializer<ReconstructorRecipe> RECONSTRUCTING_SHAPELESS =
            register("reconstructing_shapeless", new ReconstructorRecipe.Serializer());


    RecipeBookCategory RECONSTRUCTING_CAT = registerCategory("reconstructing");


    static void init() {
    }

    static <T extends Recipe<?>> RecipeType<T> register(String name) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, id(name), new RecipeType<T>() {
            public String toString() {
                return name;
            }
        });
    }

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id(name), recipeSerializer);
    }

    private static RecipeBookCategory registerCategory(String name) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, id(name), new RecipeBookCategory());
    }

}
