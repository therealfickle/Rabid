package me.therealfickle.rabid.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidLootTables {

    ResourceKey<LootTable> SUPPLY_POD = key("chests/supply_pod");

    static ResourceKey<LootTable> key(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, id(id));
    }
}
