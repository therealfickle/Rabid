package me.therealfickle.rabid.util;

import me.therealfickle.rabid.init.RabidRecipes;
import me.therealfickle.rabid.item.crafting.ReconstructorRecipe;
import me.therealfickle.rabid.item.crafting.TypedRecipeCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

public class MRCache {
    private static final TypedRecipeCache<CraftingInput, ReconstructorRecipe> RECIPE_CACHE = new TypedRecipeCache<>(RabidRecipes.RECONSTRUCTING, 10);

    public static Optional<RecipeHolder<ReconstructorRecipe>> getPotentialResults(ServerLevel serverLevel, CraftingInput craftingInput) {
        return RECIPE_CACHE.get(serverLevel, craftingInput);
    }
}
