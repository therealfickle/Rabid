package me.therealfickle.rabid.block.entity;

import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MatterReconstructorBlockEntity extends RandomizableContainerBlockEntity implements CraftingContainer {
    private static final Component DEFAULT_NAME = Component.translatable("container.rabid.matter_reconstructor");
    public static final int CRAFTING_SLOTS = 9;
    public static final int SLOTS = CRAFTING_SLOTS + 1;
    public static final int GRID_WIDTH = 3;
    public static final int GRID_HEIGHT = 3;
    private NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    int assemblyTime = 0;
    int fuel = 0;

    protected final ContainerData containerData = new MRContainerData(this);

    public MatterReconstructorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RabidBlockEntityTypes.MATTER_RECONSTRUCTOR, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    public int getWidth() {
        return GRID_WIDTH;
    }

    @Override
    public int getHeight() {
        return GRID_HEIGHT;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public CraftingInput.Positioned asPositionedCraftInput() {
        return CraftingInput.ofPositioned(getWidth(), getHeight(), getItems().subList(1, SLOTS));
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory inventory) {
        return new MatterReconstructorMenu(syncId, inventory, this, containerData);
    }

    @Override
    public int getContainerSize() {
        return SLOTS;
    }

    public static final String ASSEMBLY_TIME = "assembly_time";
    public static final String FUEL = "fuel";

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
        assemblyTime = input.getShortOr(ASSEMBLY_TIME, (short) 0);
        fuel = input.getByteOr(FUEL, (byte) 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putShort(ASSEMBLY_TIME, (short) assemblyTime);
        output.putByte(FUEL, (byte) fuel);
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents) {

    }
}
