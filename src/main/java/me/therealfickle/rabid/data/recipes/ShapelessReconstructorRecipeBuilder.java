package me.therealfickle.rabid.data.recipes;

import me.therealfickle.rabid.item.crafting.ReconstructingBookCategory;
import me.therealfickle.rabid.item.crafting.ShapelessReconstructorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ShapelessReconstructorRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final ReconstructingBookCategory category;
    private final int assemblyTime;
    private final ItemStack result;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    private ShapelessReconstructorRecipeBuilder(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemStack itemStack) {
        items = holderGetter;
        category = recipeCategory;
        this.assemblyTime = assemblyTime;
        result = itemStack;
    }

    public static ShapelessReconstructorRecipeBuilder reconstructorShapeless(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemStack itemStack) {
        return new ShapelessReconstructorRecipeBuilder(holderGetter, recipeCategory, assemblyTime, itemStack);
    }

    public static ShapelessReconstructorRecipeBuilder reconstructorShapeless(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike) {
        return reconstructorShapeless(holderGetter, recipeCategory, assemblyTime, itemLike, 1);
    }

    public static ShapelessReconstructorRecipeBuilder reconstructorShapeless(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike, int i) {
        return new ShapelessReconstructorRecipeBuilder(holderGetter, recipeCategory, assemblyTime, itemLike.asItem().getDefaultInstance().copyWithCount(i));
    }

    public ShapelessReconstructorRecipeBuilder requires(TagKey<Item> tagKey) {
        return requires(Ingredient.of(items.getOrThrow(tagKey)));
    }

    public ShapelessReconstructorRecipeBuilder requires(ItemLike itemLike) {
        return requires(itemLike, 1);
    }

    public ShapelessReconstructorRecipeBuilder requires(ItemLike itemLike, int i) {
        for (int j = 0; j < i; j++) {
            requires(Ingredient.of(itemLike));
        }

        return this;
    }

    public ShapelessReconstructorRecipeBuilder requires(Ingredient ingredient) {
        return requires(ingredient, 1);
    }

    public ShapelessReconstructorRecipeBuilder requires(Ingredient ingredient, int i) {
        for (int j = 0; j < i; j++) {
            ingredients.add(ingredient);
        }

        return this;
    }

    public ShapelessReconstructorRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
        criteria.put(string, criterion);
        return this;
    }

    public ShapelessReconstructorRecipeBuilder group(@Nullable String string) {
        group = string;
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        ensureValid(resourceKey);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(builder::addCriterion);
        ShapelessReconstructorRecipe recipe = new ShapelessReconstructorRecipe(
                Objects.requireNonNullElse(group, ""), category, assemblyTime, result,
                ingredients);
        recipeOutput.accept(resourceKey, recipe, builder.build(resourceKey.identifier().withPrefix("recipes/" + category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> resourceKey) {
        if (criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceKey.identifier());
        }
    }
}