package me.therealfickle.rabid.datagen.data.worldgen;

import me.therealfickle.rabid.data.tags.RabidBiomeTags;
import me.therealfickle.rabid.data.worldgen.RabidStructureSets;
import me.therealfickle.rabid.data.worldgen.RabidStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;

public interface RStructureSets {

    static void bootstrap(BootstrapContext<StructureSet> ctx) {
        var structures = ctx.lookup(Registries.STRUCTURE);
        var biomes = ctx.lookup(Registries.BIOME);
        ctx.register(
                RabidStructureSets.SUPPLY_POD,
                new StructureSet(
                        structures.getOrThrow(RabidStructures.SUPPLY_POD),
                        new ConcentricRingsStructurePlacement(
                                10, 3, 1, biomes.getOrThrow(RabidBiomeTags.HAS_SUPPLY_POD)
                        )
                )
        );
    }
}
