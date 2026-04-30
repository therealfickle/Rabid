package me.therealfickle.rabid.block.entity;

import me.therealfickle.rabid.Rabid;
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
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MatterReconstructorBlockEntity extends RandomizableContainerBlockEntity implements CraftingContainer {
    private static final Component DEFAULT_NAME = Component.translatable("container.rabid.matter_reconstructor");
    public static final int CRAFTING_SLOT_COUNT = 9;
    public static final int SLOT_COUNT = CRAFTING_SLOT_COUNT + 1;
    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    int assemblyTime = 0;
    int fuel = 0;

    protected final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> MatterReconstructorBlockEntity.this.assemblyTime;
                case 1 -> MatterReconstructorBlockEntity.this.fuel;
                default -> 0;
            };
        }

        @Override
        public void set(int i, int j) {
            switch (i) {
                case 0:
                    MatterReconstructorBlockEntity.this.assemblyTime = j;
                    break;
                case 1:
                    MatterReconstructorBlockEntity.this.fuel = j;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public MatterReconstructorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RabidBlockEntityTypes.MATTER_RECONSTRUCTOR, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        Rabid.LOGGER.info("open inv!");
        return new MatterReconstructorMenu(i, inventory, this, containerData);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    public static String ASSEMBLY_TIME = "assembly_time";
    public static String FUEL = "fuel";

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
