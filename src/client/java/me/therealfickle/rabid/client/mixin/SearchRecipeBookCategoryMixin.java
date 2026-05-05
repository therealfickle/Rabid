package me.therealfickle.rabid.client.mixin;

import me.therealfickle.rabid.init.RabidRecipes;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SearchRecipeBookCategory.class)
public enum SearchRecipeBookCategoryMixin {

    RABID_RECONSTRUCTING(
            RabidRecipes.RECONSTRUCTING_BUILDING,
            RabidRecipes.RECONSTRUCTING_MATERIALS,
            RabidRecipes.RECONSTRUCTING_EQUIPMENT,
            RabidRecipes.RECONSTRUCTING_MISC
    );

    @Shadow
    SearchRecipeBookCategoryMixin(final RecipeBookCategory... recipeBookCategorys) {
    }

}
