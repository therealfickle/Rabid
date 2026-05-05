package me.therealfickle.rabid.inventory;

import me.therealfickle.rabid.block.entity.MRContainerData;
import me.therealfickle.rabid.block.entity.MatterReconstructorBlockEntity;
import me.therealfickle.rabid.init.RabidMenuTypes;
import me.therealfickle.rabid.item.crafting.ReconstructorRecipe;
import me.therealfickle.rabid.util.MRCache;
import net.minecraft.core.NonNullList;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

import static me.therealfickle.rabid.mixins.TransientCraftingContainerAccessor.rabid_createTransientCraftingContainer;

public class MatterReconstructorMenu extends RecipeBookMenu implements ContainerListener {
    private static final int USE_ROW_SLOT_END = 45;

    private final ResultContainer resultContainer = new ResultContainer();
    private final ContainerData mrData;
    private final Player player;
    private final CraftingContainer container;

    public MatterReconstructorMenu(int i, Inventory inventory) {
        super(RabidMenuTypes.MATTER_RECONSTRUCTOR, i);
        player = inventory.player;
        mrData = new SimpleContainerData(MRContainerData.DATA_COUNT);
        container = rabid_createTransientCraftingContainer(this, 3, 3, NonNullList.withSize(MatterReconstructorBlockEntity.SLOTS, ItemStack.EMPTY));
        addSlots(inventory);
    }

    public MatterReconstructorMenu(int i, Inventory inventory, CraftingContainer cContainer, ContainerData data) {
        super(RabidMenuTypes.MATTER_RECONSTRUCTOR, i);
        player = inventory.player;
        mrData = data;
        container = cContainer;
        checkContainerSize(container, MatterReconstructorBlockEntity.SLOTS);
        container.startOpen(inventory.player);
        addSlots(inventory);
        addSlotListener(this);
    }

    private void addSlots(Inventory inventory) {
        addSlot(new MRFuelSlot(container, 0, 26, 17 + 18));
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int slotId = x + y * 3;
                addSlot(new Slot(container, slotId + 1, 62 + x * 18, 17 + y * 18));
            }
        }

        addSlot(new NonInteractiveResultSlot(resultContainer, 0, 134, 35));
        addStandardInventorySlots(inventory, 8, 84);
        addDataSlots(mrData);
        refreshRecipeResult();
    }

    public static final int MAX_SLOTS = MatterReconstructorBlockEntity.SLOTS;

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotId);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (slotId < MAX_SLOTS) {
                if (!moveItemStackTo(itemStack2, MAX_SLOTS, USE_ROW_SLOT_END + 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemStack2, 0, MAX_SLOTS, false)) {
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

    public void clearCrafting() {
        for (int i = 1; i < container.getContainerSize(); i++) {
            container.setItem(i, ItemStack.EMPTY);
        }
    }

    public ItemStack getResultItem() {
        return resultContainer.getItem(0);
    }

    public List<Slot> getCraftingSlots(){
        return slots.subList(1, 10);
    }

    public Slot getResultSlot(){
        return slots.get(10);
    }

    public boolean hasResult() {
        return !getResultItem().isEmpty();
    }

    public int getAssemblyTime() {
        return mrData.get(0);
    }

    public int getFuel() {
        return mrData.get(1);
    }

    public boolean isPowered() {
        return mrData.get(2) == 1;
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

    @Override
    public PostPlaceAction handlePlacement(boolean bl, boolean bl2, RecipeHolder<?> recipeHolder, ServerLevel serverLevel, Inventory inventory) {
        @SuppressWarnings("unchecked")
        var holder = (RecipeHolder<ReconstructorRecipe>) recipeHolder;

        List<Slot> craftingSlots = getCraftingSlots();

        return ServerPlaceRecipe.placeRecipe(
                new ServerPlaceRecipe.CraftingMenuAccess<>() {
                    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
                        MatterReconstructorMenu.this.fillCraftSlotsStackedContents(stackedItemContents);
                    }

                    public void clearCraftingContent() {
                        MatterReconstructorMenu.this.clearCrafting();
                        MatterReconstructorMenu.this.refreshRecipeResult();
                    }

                    public boolean recipeMatches(RecipeHolder<ReconstructorRecipe> recipeHolder1) {
                        return recipeHolder1.value().matches(MatterReconstructorMenu.this.container.asCraftInput(), MatterReconstructorMenu.this.player.level());
                    }
                },
                MatterReconstructorBlockEntity.GRID_WIDTH,
                MatterReconstructorBlockEntity.GRID_HEIGHT,
                craftingSlots, craftingSlots, inventory, holder, bl, bl2
        );
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
        container.fillStackedContents(stackedItemContents);
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.RABID_RECONSTRUCTING;
    }
}
