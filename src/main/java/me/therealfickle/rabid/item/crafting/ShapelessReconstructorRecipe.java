package me.therealfickle.rabid.item.crafting;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;


public class ShapelessReconstructorRecipe implements ReconstructorRecipe {
    final String group;
    final ReconstructingBookCategory category;
    final ItemStack result;
    final int assemblyTime;
    final List<Ingredient> ingredients;
    @Nullable
    private PlacementInfo placementInfo;

    public ShapelessReconstructorRecipe(String string, ReconstructingBookCategory craftingBookCategory, int assemblyTime, ItemStack itemStack, List<Ingredient> list) {
        this.group = string;
        this.category = craftingBookCategory;
        this.assemblyTime = assemblyTime;
        this.result = itemStack;
        this.ingredients = list;
    }

    @Override
    public RecipeSerializer<ShapelessReconstructorRecipe> getSerializer() {
        return RabidRecipes.RECONSTRUCTING_SHAPELESS;
    }

    @Override
    public int getAssemblyTime() {
        return assemblyTime;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public ReconstructingBookCategory category() {
        return this.category;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredients);
        }

        return this.placementInfo;
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

    public static class Serializer implements RecipeSerializer<ShapelessReconstructorRecipe> {

        final MapCodec<ShapelessReconstructorRecipe> codec;
        final StreamCodec<RegistryFriendlyByteBuf, ShapelessReconstructorRecipe> streamCodec;

        public Serializer(int defaultTime) {
            codec = RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                                    Codec.STRING.optionalFieldOf("group", "").forGetter(ShapelessReconstructorRecipe::group),
                                    ReconstructingBookCategory.CODEC.fieldOf("category").orElse(ReconstructingBookCategory.MISC).forGetter(ShapelessReconstructorRecipe::category),
                                    Codec.INT.fieldOf("assembly_time").orElse(defaultTime).forGetter(ShapelessReconstructorRecipe::getAssemblyTime),
                                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                    Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
                            )
                            .apply(instance, ShapelessReconstructorRecipe::new)
            );
            streamCodec = StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    ShapelessReconstructorRecipe::group,
                    ReconstructingBookCategory.STREAM_CODEC,
                    ShapelessReconstructorRecipe::category,
                    ByteBufCodecs.INT,
                    ShapelessReconstructorRecipe::getAssemblyTime,
                    ItemStack.STREAM_CODEC,
                    recipe -> recipe.result,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    recipe -> recipe.ingredients,
                    ShapelessReconstructorRecipe::new
            );
        }

        @Override
        public MapCodec<ShapelessReconstructorRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessReconstructorRecipe> streamCodec() {
            return streamCodec;
        }

    }

}
