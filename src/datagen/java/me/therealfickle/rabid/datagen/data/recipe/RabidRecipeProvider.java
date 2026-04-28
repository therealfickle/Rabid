package me.therealfickle.rabid.datagen.data.recipe;

import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class RabidRecipeProvider extends FabricRecipeProvider {
    public RabidRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider lookup, @NonNull RecipeOutput exporter) {
        return new RabidRecipeGen(lookup, exporter);
    }

    @Override
    public @NonNull String getName() {
        return "Rabid recipes";
    }


    public static class RabidRecipeGen extends RecipeProvider {
        protected RabidRecipeGen(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            super(provider, recipeOutput);
        }

        @Override
        public void buildRecipes() {
            // RECIPE HERE !!!!
            // Check "VanillaRecipeProvider.class" to see how vanilla does things
            nineBlockStorageRecipesWithCustomPacking(
                    RecipeCategory.MISC,
                    RabidItems.FICLIUM_INGOT,
                    RecipeCategory.BUILDING_BLOCKS,
                    RabidBlocks.FICLIUM_BLOCK,
                    "ficlium_ingot_from_ficlium_block", "ficlium_ingot"
            );

            nineBlockStorageRecipesWithCustomPacking(
                    RecipeCategory.MISC,
                    RabidItems.POLONIUM_NUGGET,
                    RecipeCategory.MISC,
                    RabidItems.POLONIUM_PELLET,
                    "polonium_pellet_from_nuggets",
                    "polonium_pellet"
            );
        }
    }

}
