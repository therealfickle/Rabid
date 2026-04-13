package me.therealfickle.rabid.datagen.data.loot_table;

import me.therealfickle.rabid.data.RabidLootTables;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem;

public class ChestLootTables extends SimpleFabricLootTableProvider {
    public ChestLootTables(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> gen) {

        gen.accept(
                RabidLootTables.SUPPLY_POD,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(lootTableItem(RabidItems.EXPERIMENTAL_HELR_CALLER))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(lootTableItem(RabidBlocks.FICKLE_PLUSH))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(3f, 4f))
                                        .add(
                                                lootTableItem(Items.REDSTONE)
                                                        .setWeight(5)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F)))
                                        )

                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 3f))
                                        .add(
                                                lootTableItem(Items.IRON_NUGGET)
                                                        .setWeight(3)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F)))
                                        )
                                        .add(
                                                lootTableItem(Items.IRON_INGOT)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 3f))
                                        .add(
                                                lootTableItem(RabidItems.POLONIUM_PELLET)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 3f))
                                        .add(
                                                lootTableItem(RabidItems.POLONIUM_NUGGET)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F)))
                                        )

                        )
        );

    }
}
