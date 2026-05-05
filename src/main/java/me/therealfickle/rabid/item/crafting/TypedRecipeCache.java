package me.therealfickle.rabid.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Optional;

public class TypedRecipeCache<I extends RecipeInput, R extends Recipe<I>> {
    public final RecipeType<R> recipeType;
    private final Entry<I, R>[] entries;
    private WeakReference<RecipeManager> cachedRecipeManager = new WeakReference<>(null);

    public TypedRecipeCache(RecipeType<R> recipeType, int i) {
        this.recipeType = recipeType;
        //noinspection unchecked
        this.entries = new Entry[i];
    }

    public Optional<RecipeHolder<R>> get(ServerLevel serverLevel, I input) {
        if (input.isEmpty()) {
            return Optional.empty();
        } else {
            this.validateRecipeManager(serverLevel);

            for (int i = 0; i < this.entries.length; i++) {
                Entry<I, R> entry = this.entries[i];
                if (entry != null && entry.matches(serverLevel, input)) {
                    this.moveEntryToFront(i);
                    return Optional.ofNullable(entry.value());
                }
            }

            return this.compute(input, serverLevel);
        }
    }

    private void validateRecipeManager(ServerLevel serverLevel) {
        RecipeManager recipeManager = serverLevel.recipeAccess();
        if (recipeManager != this.cachedRecipeManager.get()) {
            this.cachedRecipeManager = new WeakReference<>(recipeManager);
            Arrays.fill(this.entries, null);
        }
    }

    private Optional<RecipeHolder<R>> compute(I craftingInput, ServerLevel serverLevel) {
        Optional<RecipeHolder<R>> optional = serverLevel.recipeAccess().getRecipeFor(recipeType, craftingInput, serverLevel);
        this.insert(craftingInput, optional.orElse(null));
        return optional;
    }

    private void moveEntryToFront(int i) {
        if (i > 0) {
            Entry<I, R> entry = this.entries[i];
            System.arraycopy(this.entries, 0, this.entries, 1, i);
            this.entries[0] = entry;
        }
    }

    private void insert(I input, @Nullable RecipeHolder<R> recipeHolder) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < input.size(); i++) {
            nonNullList.set(i, input.getItem(i).copyWithCount(1));
        }

        System.arraycopy(this.entries, 0, this.entries, 1, this.entries.length - 1);

        var width = 1;
        var height = 1;
        if (input instanceof CraftingInput craftingInput) {
            width = craftingInput.width();
            height = craftingInput.height();
        }
        this.entries[0] = new Entry<>(nonNullList, width, height, recipeHolder);
    }

    record Entry<I extends RecipeInput, R extends Recipe<I>>(NonNullList<ItemStack> key, int width, int height,
                                                             @Nullable RecipeHolder<R> value) {
        public boolean matches(ServerLevel serverLevel, I input) {
            if (input instanceof CraftingInput craftingInput) {
                if (this.width == craftingInput.width() && this.height == craftingInput.height()) {
                    for (int i = 0; i < this.key.size(); i++) {
                        if (!ItemStack.isSameItemSameComponents(this.key.get(i), input.getItem(i))) {
                            return false;
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
            return value != null && value.value().matches(input, serverLevel);
        }
    }
}
