package me.therealfickle.rabid.init;

import me.therealfickle.rabid.block.entity.FicliumCrateBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface RabidBlockEntityTypes {
    BlockEntityType<FicliumCrateBlockEntity> FICLIUM_CRATE = register("ficlium_crate", FicliumCrateBlockEntity::new, RabidBlocks.FICLIUM_CRATE);

    static void init() {
    }

    static <T extends BlockEntity> BlockEntityType<T> register(String string, FabricBlockEntityTypeBuilder.Factory<T> blockEntitySupplier, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, string, FabricBlockEntityTypeBuilder.create(blockEntitySupplier, blocks).build());
    }

}
