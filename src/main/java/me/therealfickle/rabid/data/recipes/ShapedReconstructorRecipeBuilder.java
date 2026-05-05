package me.therealfickle.rabid.data.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.therealfickle.rabid.item.crafting.ReconstructingBookCategory;
import me.therealfickle.rabid.item.crafting.ShapedReconstructorRecipe;
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
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShapedReconstructorRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final ReconstructingBookCategory category;
    private final Item result;
    private final int count;
    final int assemblyTime;
    private final List<String> rows = Lists.<String>newArrayList();
    private final Map<Character, Ingredient> key = Maps.<Character, Ingredient>newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    @Nullable
    private String group;
    private boolean showNotification = true;

    private ShapedReconstructorRecipeBuilder(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike, int i) {
        this.items = holderGetter;
        this.category = recipeCategory;
        this.result = itemLike.asItem();
        this.count = i;
        this.assemblyTime = assemblyTime;
    }

    public static ShapedReconstructorRecipeBuilder reconstructorShaped(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike) {
        return reconstructorShaped(holderGetter, recipeCategory, assemblyTime, itemLike, 1);
    }

    public static ShapedReconstructorRecipeBuilder reconstructorShaped(HolderGetter<Item> holderGetter, ReconstructingBookCategory recipeCategory, int assemblyTime, ItemLike itemLike, int i) {
        return new ShapedReconstructorRecipeBuilder(holderGetter, recipeCategory, assemblyTime, itemLike, i);
    }

    public ShapedReconstructorRecipeBuilder define(Character character, TagKey<Item> tagKey) {
        return this.define(character, Ingredient.of(this.items.getOrThrow(tagKey)));
    }

    public ShapedReconstructorRecipeBuilder define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public ShapedReconstructorRecipeBuilder define(Character character, Ingredient ingredient) {
        if (this.key.containsKey(character)) {
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        } else if (character == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(character, ingredient);
            return this;
        }
    }

    public ShapedReconstructorRecipeBuilder pattern(String string) {
        if (!this.rows.isEmpty() && string.length() != ((String) this.rows.get(0)).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(string);
            return this;
        }
    }

    public ShapedReconstructorRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
        this.criteria.put(string, criterion);
        return this;
    }

    public ShapedReconstructorRecipeBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public ShapedReconstructorRecipeBuilder showNotification(boolean bl) {
        this.showNotification = bl;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        ShapedRecipePattern shapedRecipePattern = this.ensureValid(resourceKey);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        var shapedRecipe = new ShapedReconstructorRecipe(
                (String) Objects.requireNonNullElse(this.group, ""),
                this.category,
                shapedRecipePattern,
                new ItemStack(this.result, this.count),
                assemblyTime,
                showNotification
        );
        recipeOutput.accept(resourceKey, shapedRecipe, builder.build(resourceKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private ShapedRecipePattern ensureValid(ResourceKey<Recipe<?>> resourceKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceKey.identifier());
        } else {
            return ShapedRecipePattern.of(this.key, this.rows);
        }
    }
}
