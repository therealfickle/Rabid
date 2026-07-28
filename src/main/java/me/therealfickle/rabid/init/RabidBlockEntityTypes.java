package me.therealfickle.rabid.init;

import me.therealfickle.rabid.block.entity.FicklePlushBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidBlockEntityTypes {

    BlockEntityType<FicklePlushBlockEntity> FICKLE_PLUSH = register("fickle_plush", FicklePlushBlockEntity::new, RabidBlocks.FICKLE_PLUSH);

    static void init() {
    }

    static <T extends BlockEntity> BlockEntityType<T> register(String string, FabricBlockEntityTypeBuilder.Factory<T> blockEntitySupplier, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(string), FabricBlockEntityTypeBuilder.create(blockEntitySupplier, blocks).build());
    }

}
