package me.therealfickle.rabid.item.crafting;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;


public class ReconstructorRecipe implements Recipe<CraftingInput> {
    final String group;
    final CraftingBookCategory category;
    final ItemStack result;
    final List<Ingredient> ingredients;
    @Nullable
    private PlacementInfo placementInfo;

    public ReconstructorRecipe(String string, CraftingBookCategory craftingBookCategory, ItemStack itemStack, List<Ingredient> list) {
        this.group = string;
        this.category = craftingBookCategory;
        this.result = itemStack;
        this.ingredients = list;
    }

    @Override
    public RecipeType<? extends Recipe<CraftingInput>> getType() {
        return RabidRecipes.RECONSTRUCTING;
    }

    @Override
    public RecipeSerializer<ReconstructorRecipe> getSerializer() {
        return RabidRecipes.RECONSTRUCTING_SHAPELESS;
    }


    @Override
    public String group() {
        return this.group;
    }

    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredients);
        }

        return this.placementInfo;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonNullList.size(); i++) {
            Item item = craftingInput.getItem(i).getItem();
            nonNullList.set(i, item.getCraftingRemainder());
        }

        return nonNullList;
    }

    public boolean matches(CraftingInput craftingInput, Level level) {
        if (craftingInput.ingredientCount() != this.ingredients.size()) {
            return false;
        } else {
            return craftingInput.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(craftingInput.getItem(0))
                    : craftingInput.stackedContents().canCraft(this, null);
        }
    }

    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        this.ingredients.stream().map(Ingredient::display).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(RabidBlocks.MATTER_RECONSTRUCTOR.asItem())
                )
        );
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RabidRecipes.RECONSTRUCTING_CAT;
    }

    public static class Serializer implements RecipeSerializer<ReconstructorRecipe> {
        private static final MapCodec<ReconstructorRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(ReconstructorRecipe::group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ReconstructorRecipe::category),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
                        )
                        .apply(instance, ReconstructorRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ReconstructorRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                ReconstructorRecipe::group,
                CraftingBookCategory.STREAM_CODEC,
                ReconstructorRecipe::category,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.result,
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                recipe -> recipe.ingredients,
                ReconstructorRecipe::new
        );

        @Override
        public MapCodec<ReconstructorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ReconstructorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
