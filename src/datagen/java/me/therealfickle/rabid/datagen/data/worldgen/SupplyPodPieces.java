package me.therealfickle.rabid.datagen.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import static me.therealfickle.rabid.Rabid.id;

public interface SupplyPodPieces {
    ResourceKey<StructureTemplatePool> POD = createKey("pod");

    static void bootstrap(BootstrapContext<StructureTemplatePool> bootstrapContext) {
//        var procLists = bootstrapContext.lookup(Registries.PROCESSOR_LIST);
        var pools = bootstrapContext.lookup(Registries.TEMPLATE_POOL);
        bootstrapContext.register(
                POD,
                new StructureTemplatePool(
                        pools.getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(StructurePoolElement.single("rabid:supply_pod/pod"), 1)),
                        StructureTemplatePool.Projection.TERRAIN_MATCHING
                )
        );
    }

    static ResourceKey<StructureTemplatePool> createKey(String string) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, id(string));
    }

}
