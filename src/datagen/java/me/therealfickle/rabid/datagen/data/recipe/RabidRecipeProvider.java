package me.therealfickle.rabid.datagen.data.recipe;

import me.therealfickle.rabid.data.recipes.ReconstructorRecipeBuilder;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static me.therealfickle.rabid.datagen.data.recipe.RecipeHelpers.sixItemStorageRecipes;

public class RabidRecipeProvider extends FabricRecipeProvider {
    public RabidRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookup, RecipeOutput exporter) {
        return new RabidRecipeGen(lookup, exporter);
    }

    @Override
    public String getName() {
        return "Rabid recipes";
    }


    public static class RabidRecipeGen extends RecipeProvider {
        private final HolderLookup.RegistryLookup<Item> items2;

        protected RabidRecipeGen(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            super(provider, recipeOutput);
            items2 = provider.lookupOrThrow(Registries.ITEM);
        }

        @Override
        public void buildRecipes() {
            // RECIPE HERE !!!!
            // Check "VanillaRecipeProvider.class" to see how vanilla does things
            nineBlockStorageRecipesWithCustomPacking(
                    RecipeCategory.MISC,
                    RabidItems.SFA_INGOT,
                    RecipeCategory.BUILDING_BLOCKS,
                    RabidBlocks.SFA_BLOCK,
                    "sfa_ingot_from_sfa_block", "sfa_ingot"
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

            reconstructor(RecipeCategory.MISC, RabidItems.SFA_INGOT)
                    .requires(Items.IRON_INGOT, 4)
                    .requires(Items.OBSIDIAN, 4)
                    .unlockedBy(RecipeProvider.getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                    .save(output(), "testing_recipe_now");

        }

        public ReconstructorRecipeBuilder reconstructor(RecipeCategory recipeCategory, ItemLike itemLike) {
            return ReconstructorRecipeBuilder.reconstructor(items2, recipeCategory, itemLike);
        }

        public ReconstructorRecipeBuilder reconstructor(RecipeCategory recipeCategory, ItemLike itemLike, int i) {
            return ReconstructorRecipeBuilder.reconstructor(items2, recipeCategory, itemLike, i);
        }

        public RecipeOutput output() {
            return output;
        }

    }

}
