package me.therealfickle.rabid.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidStructureSets {

    ResourceKey<StructureSet> SUPPLY_POD = key("supply_pod");

    static ResourceKey<StructureSet> key(String id) {
        return ResourceKey.create(Registries.STRUCTURE_SET, id(id));
    }
}
