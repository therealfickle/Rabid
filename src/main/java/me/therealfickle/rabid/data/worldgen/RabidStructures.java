package me.therealfickle.rabid.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidStructures {

    ResourceKey<Structure> SUPPLY_POD = key("supply_pod");

    static ResourceKey<Structure> key(String id) {
        return ResourceKey.create(Registries.STRUCTURE, id(id));
    }
}
