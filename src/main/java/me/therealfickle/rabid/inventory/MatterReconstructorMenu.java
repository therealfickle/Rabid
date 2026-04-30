package me.therealfickle.rabid.inventory;

import me.therealfickle.rabid.init.RabidMenuTypes;
import me.therealfickle.rabid.util.MRCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

public class MatterReconstructorMenu extends AbstractContainerMenu implements ContainerListener {
    private static final int USE_ROW_SLOT_END = 45;

    private final ResultContainer resultContainer = new ResultContainer();
    private final ContainerData containerData;
    private final Player player;
    private final CraftingContainer container;

    public MatterReconstructorMenu(int i, Inventory inventory) {
        super(RabidMenuTypes.MATTER_RECONSTRUCTOR, i);
        player = inventory.player;
        containerData = new SimpleContainerData(10);
        container = new TransientCraftingContainer(this, 3, 3);
        addSlots(inventory);
    }

    public MatterReconstructorMenu(int i, Inventory inventory, CraftingContainer craftingContainer, ContainerData data) {
        super(RabidMenuTypes.MATTER_RECONSTRUCTOR, i);
        player = inventory.player;
        containerData = data;
        container = craftingContainer;
        checkContainerSize(craftingContainer, 9);
        craftingContainer.startOpen(inventory.player);
        addSlots(inventory);
        addSlotListener(this);
    }

    private void addSlots(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int k = j + i * 3;
                addSlot(new Slot(container, k, 26 + j * 18, 17 + i * 18));
            }
        }

        addStandardInventorySlots(inventory, 8, 84);
        addSlot(new NonInteractiveResultSlot(resultContainer, 0, 134, 35));
        addDataSlots(containerData);
        refreshRecipeResult();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < 9) {
                if (!moveItemStackTo(itemStack2, 9, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemStack2, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    private void refreshRecipeResult() {
        if (player.level() instanceof ServerLevel serverLevel) {
            CraftingInput craftingInput = container.asCraftInput();
            ItemStack itemStack = MRCache.getPotentialResults(serverLevel, craftingInput)
                    .map(recipeHolder -> recipeHolder.value().assemble(craftingInput, serverLevel.registryAccess()))
                    .orElse(ItemStack.EMPTY);
            resultContainer.setItem(0, itemStack);
        }
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public void slotChanged(AbstractContainerMenu menu, int i, ItemStack itemStack) {
        refreshRecipeResult();
    }

    @Override
    public void dataChanged(AbstractContainerMenu menu, int i, int j) {
    }

}
