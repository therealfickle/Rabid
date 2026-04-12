package me.therealfickle.rabid.block.entity;

import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

public class FicliumCrateBlockEntity extends RandomizableContainerBlockEntity {
    private static final Component DEFAULT_NAME = Component.translatable("container.ficlium_crate");
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            FicliumCrateBlockEntity.this.playSound(blockState, SoundEvents.BARREL_OPEN);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            FicliumCrateBlockEntity.this.playSound(blockState, SoundEvents.BARREL_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
        }

        @Override
        public boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu) player.containerMenu).getContainer();
                return container == FicliumCrateBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public FicliumCrateBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RabidBlockEntityTypes.FICLIUM_CRATE, blockPos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!trySaveLootTable(valueOutput)) {
            ContainerHelper.saveAllItems(valueOutput, items);
        }
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!tryLoadLootTable(valueInput)) {
            ContainerHelper.loadAllItems(valueInput, items);
        }
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.threeRows(i, inventory, this);
    }

    @Override
    public void startOpen(ContainerUser containerUser) {
        if (!remove && !containerUser.getLivingEntity().isSpectator()) {
            openersCounter
                    .incrementOpeners(containerUser.getLivingEntity(), getLevel(), getBlockPos(), getBlockState(), containerUser.getContainerInteractionRange());
        }
    }

    @Override
    public void stopOpen(ContainerUser containerUser) {
        if (!remove && !containerUser.getLivingEntity().isSpectator()) {
            openersCounter.decrementOpeners(containerUser.getLivingEntity(), getLevel(), getBlockPos(), getBlockState());
        }
    }

    @Override
    public List<ContainerUser> getEntitiesWithContainerOpen() {
        return openersCounter.getEntitiesWithContainerOpen(getLevel(), getBlockPos());
    }


    public void recheckOpen() {
        if (!remove) {
            openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
        }
    }


    void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vec3i vec3i = blockState.getValue(BarrelBlock.FACING).getUnitVec3i();
        double x = worldPosition.getX() + 0.5 + vec3i.getX() / 2.0;
        double y = worldPosition.getY() + 0.5 + vec3i.getY() / 2.0;
        double z = worldPosition.getZ() + 0.5 + vec3i.getZ() / 2.0;
        level.playSound(null, x, y, z, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }
}
