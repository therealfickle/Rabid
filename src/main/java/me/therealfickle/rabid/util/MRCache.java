package me.therealfickle.rabid.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeCache;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

public class MRCache {
    // TODO make cache for custom recipe type
    private static final RecipeCache RECIPE_CACHE = new RecipeCache(10);

    public static Optional<RecipeHolder<CraftingRecipe>> getPotentialResults(ServerLevel serverLevel, CraftingInput craftingInput) {
        return RECIPE_CACHE.get(serverLevel, craftingInput);
    }
}
