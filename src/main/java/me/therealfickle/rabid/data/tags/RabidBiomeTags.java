package me.therealfickle.rabid.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidBiomeTags {

    TagKey<Biome> HAS_SUPPLY_POD = key("has_structure/supply_pod");

    static TagKey<Biome> key(String id) {
        return TagKey.create(Registries.BIOME, id(id));
    }
}
