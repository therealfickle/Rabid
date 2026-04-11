package me.therealfickle.rabid.datagen;

import me.therealfickle.rabid.Rabid;
import me.therealfickle.rabid.datagen.data.tags.BiomeTagsProvider;
import me.therealfickle.rabid.datagen.data.worldgen.RStructureSets;
import me.therealfickle.rabid.datagen.data.worldgen.RStructures;
import me.therealfickle.rabid.datagen.data.worldgen.SupplyPodPieces;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RabidDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        // Data
        pack.addProvider(BiomeTagsProvider::new);
        pack.addProvider(RegistryProvider::new);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Rabid.MODID;
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder builder) {
        builder.add(Registries.STRUCTURE_SET, RStructureSets::bootstrap);
        builder.add(Registries.STRUCTURE, RStructures::bootstrap);
        builder.add(Registries.TEMPLATE_POOL, SupplyPodPieces::bootstrap);
    }

    static class RegistryProvider extends FabricDynamicRegistryProvider {

        public RegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.@NonNull Provider provider, @NonNull Entries entries) {
            entries.addAll(provider.lookupOrThrow(Registries.STRUCTURE_SET));
            entries.addAll(provider.lookupOrThrow(Registries.STRUCTURE));
            entries.addAll(provider.lookupOrThrow(Registries.TEMPLATE_POOL));
        }

        @Override
        public @NonNull String getName() {
            return "Registries";
        }
    }
}
