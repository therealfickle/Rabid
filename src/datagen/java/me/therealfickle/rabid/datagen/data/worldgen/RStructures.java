package me.therealfickle.rabid.datagen.data.worldgen;

import me.therealfickle.rabid.data.tags.RabidBiomeTags;
import me.therealfickle.rabid.data.worldgen.RabidStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;

public interface RStructures {

    static void bootstrap(BootstrapContext<Structure> ctx) {
        var biomes = ctx.lookup(Registries.BIOME);
        var templatePools = ctx.lookup(Registries.TEMPLATE_POOL);

        ctx.register(
                RabidStructures.SUPPLY_POD,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomes.getOrThrow(RabidBiomeTags.HAS_SUPPLY_POD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.NONE
                        ),
                        templatePools.getOrThrow(SupplyPodPieces.POD),
                        6,
                        ConstantHeight.of(VerticalAnchor.absolute(-1)),
                        false,
                        Heightmap.Types.OCEAN_FLOOR_WG
                )
        );
    }
}
