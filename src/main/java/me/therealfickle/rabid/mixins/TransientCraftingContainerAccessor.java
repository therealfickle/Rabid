package me.therealfickle.rabid.mixins;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TransientCraftingContainer.class)
public interface TransientCraftingContainerAccessor {
    @Invoker("<init>")
    static TransientCraftingContainer rabid_createTransientCraftingContainer(AbstractContainerMenu abstractContainerMenu, int i, int j, NonNullList<ItemStack> nonNullList) {
        throw new UnsupportedOperationException();
    }
}
