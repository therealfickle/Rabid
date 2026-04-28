package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.data.tags.RabidBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsProvider extends FabricTagProvider<Biome> {

    public BiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        builder(RabidBiomeTags.HAS_SUPPLY_POD)
                .forceAddTag(BiomeTags.IS_OVERWORLD);
    }
}
