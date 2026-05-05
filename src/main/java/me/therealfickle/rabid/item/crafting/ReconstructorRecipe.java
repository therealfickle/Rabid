package me.therealfickle.rabid.item.crafting;


import me.therealfickle.rabid.init.RabidRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;


public interface ReconstructorRecipe extends Recipe<CraftingInput> {

    @Override
    default RecipeType<ReconstructorRecipe> getType() {
        return RabidRecipes.RECONSTRUCTING;
    }

    @Override
    RecipeSerializer<? extends ReconstructorRecipe> getSerializer();

    ReconstructingBookCategory category();

    int getAssemblyTime();

    default NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput) {
        return defaultCraftingReminder(craftingInput);
    }

    static NonNullList<ItemStack> defaultCraftingReminder(CraftingInput craftingInput) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonNullList.size(); i++) {
            Item item = craftingInput.getItem(i).getItem();
            nonNullList.set(i, item.getCraftingRemainder());
        }

        return nonNullList;
    }


    @Override
    default RecipeBookCategory recipeBookCategory() {
        return switch (category()) {
            case BUILDING -> RabidRecipes.RECONSTRUCTING_BUILDING;
            case MATERIALS -> RabidRecipes.RECONSTRUCTING_MATERIALS;
            case EQUIPMENT -> RabidRecipes.RECONSTRUCTING_EQUIPMENT;
            case MISC -> RabidRecipes.RECONSTRUCTING_MISC;
        };
    }

}