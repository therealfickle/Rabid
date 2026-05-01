package me.therealfickle.rabid.block.entity;

import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import me.therealfickle.rabid.util.MRCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static me.therealfickle.rabid.Rabid.CONFIG;

public class MatterReconstructorBlockEntity extends RandomizableContainerBlockEntity implements CraftingContainer {
    private static final Component DEFAULT_NAME = Component.translatable("container.rabid.matter_reconstructor");
    public static final int CRAFTING_SLOTS = 9;
    public static final int SLOTS = CRAFTING_SLOTS + 1;
    public static final int GRID_WIDTH = 3;
    public static final int GRID_HEIGHT = 3;
    private NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    int assemblyTime = 0;
    int fuel = 0;
    public boolean isPowered = false;

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

    public List<ItemStack> getCraftingItems() {
        return getItems().subList(1, SLOTS);
    }

    @Override
    public CraftingInput.Positioned asPositionedCraftInput() {
        return CraftingInput.ofPositioned(getWidth(), getHeight(), getCraftingItems());
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
    public static final String IS_POWERED = "is_powered";

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
        assemblyTime = input.getShortOr(ASSEMBLY_TIME, (short) 0);
        fuel = input.getByteOr(FUEL, (byte) 0);
        isPowered = input.getBooleanOr(IS_POWERED, false);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putShort(ASSEMBLY_TIME, (short) assemblyTime);
        output.putByte(FUEL, (byte) fuel);
        output.putBoolean(IS_POWERED, isPowered);
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents) {
    }

    public void craftResult(RecipeHolder<CraftingRecipe> recipeHolder, CraftingInput input) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        var resultStack = recipeHolder.value().assemble(input, serverLevel.registryAccess());
        if (resultStack.isEmpty()) return;

        resultStack.onCraftedBySystem(serverLevel);


        var pos = getBlockPos();
        dispenseItems(serverLevel, pos, resultStack);

        for (ItemStack remainingStack : recipeHolder.value().getRemainingItems(input)) {
            if (!remainingStack.isEmpty()) {
                dispenseItems(serverLevel, pos, remainingStack);
            }
        }

        getCraftingItems().forEach(stack -> {
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
        });
        setChanged();
    }

    private static void dispenseItems(ServerLevel serverLevel, BlockPos pos, ItemStack itemStack) {
        Vec3 outputPos = Vec3.atCenterOf(pos).relative(Direction.UP, 0.7);
        DefaultDispenseItemBehavior.spawnItem(serverLevel, itemStack, 6, Direction.UP, outputPos);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, MatterReconstructorBlockEntity reconstructor) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (reconstructor.fuel < 0) {
            reconstructor.fuel = 0;
        }
        var itemStack = reconstructor.items.getFirst();
        if (CONFIG.matterReconstructor.hasSpaceForFuel(reconstructor.fuel) && itemStack.is(RabidItemTags.MATTER_RECONSTRUCTOR_FUELS)) {
            reconstructor.fuel += CONFIG.matterReconstructor.fuelItemValue.get();
            itemStack.shrink(1);
            setChanged(level, blockPos, blockState);
        }

        var input = reconstructor.asCraftInput();
        var optional = MRCache.getPotentialResults(serverLevel, input);

        var hasResult = optional.isPresent();

        boolean isCrafting = reconstructor.assemblyTime > 0;
        if (isCrafting) {
            if (reconstructor.isPowered && reconstructor.fuel > 0) {
                reconstructor.assemblyTime--;
            }

            if (reconstructor.assemblyTime == 0 && hasResult) {
                reconstructor.fuel -= CONFIG.matterReconstructor.fuelPerRecipe.get();
                reconstructor.craftResult(optional.get(), input);
            } else if (!hasResult) {
                reconstructor.assemblyTime = 0;
            }

            setChanged(level, blockPos, blockState);
        } else if (hasResult && reconstructor.fuel > 0) {
            // TODO make this pull from the recipe
            reconstructor.assemblyTime = 400;
            setChanged(level, blockPos, blockState);
        }
    }
}
