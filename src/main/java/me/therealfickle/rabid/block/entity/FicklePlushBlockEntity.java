package me.therealfickle.rabid.block.entity;

import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class FicklePlushBlockEntity extends BlockEntity {

    private static final String TAG_CUSTOM_NAME = "custom_name";
    @Nullable
    private Component customName;
    public long squishStartedAtTick = 0;

    public FicklePlushBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RabidBlockEntityTypes.FICKLE_PLUSH, blockPos, blockState);
    }

    public void squish() {
        if (level != null && !level.isClientSide()) {
            level.blockEvent(getBlockPos(), getBlockState().getBlock(), 1, 0);
        }
    }

    @Override
    public boolean triggerEvent(int i, int j) {
        if (level != null && i == 1 && j >= 0 && j < DecoratedPotBlockEntity.WobbleStyle.values().length) {
            squishStartedAtTick = level.getGameTime();
            return true;
        } else {
            return super.triggerEvent(i, j);
        }
    }


    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveCustomOnly(provider);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.storeNullable(TAG_CUSTOM_NAME, ComponentSerialization.CODEC, customName);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        customName = parseCustomNameSafe(valueInput, TAG_CUSTOM_NAME);
    }


    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        customName = dataComponentGetter.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.CUSTOM_NAME, customName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void removeComponentsFromTag(ValueOutput valueOutput) {
        super.removeComponentsFromTag(valueOutput);
        valueOutput.discard(TAG_CUSTOM_NAME);
    }
}
