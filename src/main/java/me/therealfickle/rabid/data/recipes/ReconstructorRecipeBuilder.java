package me.therealfickle.rabid.data.recipes;

import me.therealfickle.rabid.item.crafting.ReconstructorRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

public class ReconstructorRecipeBuilder implements RecipeBuilder {
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final ItemStack result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	private ReconstructorRecipeBuilder(HolderGetter<Item> holderGetter, RecipeCategory recipeCategory, ItemStack itemStack) {
		items = holderGetter;
		category = recipeCategory;
		result = itemStack;
	}

	public static ReconstructorRecipeBuilder reconstructor(HolderGetter<Item> holderGetter, RecipeCategory recipeCategory, ItemStack itemStack) {
		return new ReconstructorRecipeBuilder(holderGetter, recipeCategory, itemStack);
	}

	public static ReconstructorRecipeBuilder reconstructor(HolderGetter<Item> holderGetter, RecipeCategory recipeCategory, ItemLike itemLike) {
		return reconstructor(holderGetter, recipeCategory, itemLike, 1);
	}

	public static ReconstructorRecipeBuilder reconstructor(HolderGetter<Item> holderGetter, RecipeCategory recipeCategory, ItemLike itemLike, int i) {
		return new ReconstructorRecipeBuilder(holderGetter, recipeCategory, itemLike.asItem().getDefaultInstance().copyWithCount(i));
	}

	public ReconstructorRecipeBuilder requires(TagKey<Item> tagKey) {
		return requires(Ingredient.of(items.getOrThrow(tagKey)));
	}

	public ReconstructorRecipeBuilder requires(ItemLike itemLike) {
		return requires(itemLike, 1);
	}

	public ReconstructorRecipeBuilder requires(ItemLike itemLike, int i) {
		for (int j = 0; j < i; j++) {
			requires(Ingredient.of(itemLike));
		}

		return this;
	}

	public ReconstructorRecipeBuilder requires(Ingredient ingredient) {
		return requires(ingredient, 1);
	}

	public ReconstructorRecipeBuilder requires(Ingredient ingredient, int i) {
		for (int j = 0; j < i; j++) {
			ingredients.add(ingredient);
		}

		return this;
	}

	public ReconstructorRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
		criteria.put(string, criterion);
		return this;
	}

	public ReconstructorRecipeBuilder group(@Nullable String string) {
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
		ReconstructorRecipe recipe = new ReconstructorRecipe(
                Objects.requireNonNullElse(group, ""), RecipeBuilder.determineBookCategory(category), result, ingredients
		);
		recipeOutput.accept(resourceKey, recipe, builder.build(resourceKey.identifier().withPrefix("recipes/" + category.getFolderName() + "/")));
	}

	private void ensureValid(ResourceKey<Recipe<?>> resourceKey) {
		if (criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + resourceKey.identifier());
		}
	}
}