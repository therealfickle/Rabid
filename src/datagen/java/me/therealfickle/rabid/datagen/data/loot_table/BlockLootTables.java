package me.therealfickle.rabid.datagen.data.loot_table;

import me.therealfickle.rabid.init.RabidBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BlockLootTables extends FabricBlockLootTableProvider {

    public BlockLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(RabidBlocks.SFA_BLOCK);
        add(RabidBlocks.SFA_CRATE, this::createNameableBlockEntityTable);
        dropSelf(RabidBlocks.SFA_DISTRESS_LIGHT);
        dropSelf(RabidBlocks.FICKLE_PLUSH);
    }
}
