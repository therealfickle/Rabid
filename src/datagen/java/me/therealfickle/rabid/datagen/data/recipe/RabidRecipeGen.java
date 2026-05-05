package me.therealfickle.rabid.datagen.data.recipe;

import me.therealfickle.rabid.data.recipes.ShapedReconstructorRecipeBuilder;
import me.therealfickle.rabid.data.recipes.ShapelessReconstructorRecipeBuilder;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import me.therealfickle.rabid.item.crafting.ReconstructingBookCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import static me.therealfickle.rabid.datagen.data.recipe.RecipeHelpers.sixItemStorageRecipes;
import static me.therealfickle.rabid.item.crafting.ReconstructingBookCategory.BUILDING;
import static me.therealfickle.rabid.item.crafting.ReconstructingBookCategory.MATERIALS;

public class RabidRecipeGen extends RecipeProvider {
    private final HolderLookup.RegistryLookup<Item> items2;

    protected RabidRecipeGen(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
        items2 = provider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void buildRecipes() {
        // RECIPE HERE !!!!
        // Check "VanillaRecipeProvider.class" to see how vanilla does things
        nineBlockStorageRecipesRecipesWithCustomUnpacking(
                RecipeCategory.MISC,
                RabidItems.SFA_INGOT,
                RecipeCategory.BUILDING_BLOCKS,
                RabidBlocks.SFA_BLOCK,
                "sfa_ingot_from_sfa_block",
                "sfa_block"
        );

        sixItemStorageRecipes(
                this,
                RecipeCategory.MISC,
                RabidItems.POLONIUM_NUGGET,
                RecipeCategory.MISC,
                RabidItems.POLONIUM_PELLET,
                "polonium_pellet_from_nuggets",
                "polonium_pellet"
        );

        reconstructorShapeless(MATERIALS, 200, RabidItems.SFA_INGOT)
                .requires(Items.IRON_INGOT, 4)
                .requires(Items.OBSIDIAN, 4)
                .unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                .save(output());

        reconstructorShaped(BUILDING, 100, RabidBlocks.SFA_CRATE)
                .define('C', Blocks.CHEST)
                .define('F', RabidItems.SFA_INGOT)
                .pattern(" F ")
                .pattern("FCF")
                .pattern(" F ")
                .unlockedBy(getHasName(RabidItems.SFA_INGOT), has(RabidItems.SFA_INGOT))
                .save(output());

    }

    public ShapelessReconstructorRecipeBuilder reconstructorShapeless(ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike) {
        return ShapelessReconstructorRecipeBuilder.reconstructorShapeless(items2, recipeCategory, assemblyTime, itemLike);
    }

    public ShapelessReconstructorRecipeBuilder reconstructorShapeless(ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike, int i) {
        return ShapelessReconstructorRecipeBuilder.reconstructorShapeless(items2, recipeCategory, assemblyTime, itemLike, i);
    }

    public ShapedReconstructorRecipeBuilder reconstructorShaped(ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike) {
        return ShapedReconstructorRecipeBuilder.reconstructorShaped(items2, recipeCategory, assemblyTime, itemLike);
    }

    public ShapedReconstructorRecipeBuilder reconstructorShaped(ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike, int i) {
        return ShapedReconstructorRecipeBuilder.reconstructorShaped(items2, recipeCategory, assemblyTime, itemLike, i);
    }

    public RecipeOutput output() {
        return output;
    }

}
