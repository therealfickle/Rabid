package me.therealfickle.rabid.datagen.data.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import static net.minecraft.data.recipes.RecipeProvider.getSimpleRecipeName;

public interface RecipeHelpers {
    static void sixItemStorageRecipes(
            RabidRecipeProvider.RabidRecipeGen provider,
            RecipeCategory recipeCategory,
            ItemLike itemLike,
            RecipeCategory recipeCategory2,
            ItemLike itemLike2,
            String string,
            String string2
    ) {
        sixItemStorageRecipes(provider, recipeCategory, itemLike, recipeCategory2, itemLike2, string, string2, getSimpleRecipeName(itemLike), null);
    }

    static void sixItemStorageRecipes(
            RabidRecipeProvider.RabidRecipeGen provider,
            RecipeCategory recipeCategory,
            ItemLike itemLike,
            RecipeCategory recipeCategory2,
            ItemLike itemLike2,
            String string,
            @Nullable String string2,
            String string3,
            @Nullable String string4
    ) {
        provider.shapeless(recipeCategory, itemLike, 6)
                .requires(itemLike2)
                .group(string4)
                .unlockedBy(RecipeProvider.getHasName(itemLike2), provider.has(itemLike2))
                .save(provider.output(), ResourceKey.create(Registries.RECIPE, Identifier.parse(string3)));
        provider.shapeless(recipeCategory2, itemLike2)
                .requires(itemLike, 6)
                .group(string2)
                .unlockedBy(RecipeProvider.getHasName(itemLike), provider.has(itemLike))
                .save(provider.output(), ResourceKey.create(Registries.RECIPE, Identifier.parse(string)));
    }
}
