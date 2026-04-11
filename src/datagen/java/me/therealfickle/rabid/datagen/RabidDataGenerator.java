package me.therealfickle.rabid.datagen;

import me.therealfickle.rabid.Rabid;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RabidDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RegistryProvider::new);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Rabid.MODID;
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder registryBuilder) {

    }

    static class RegistryProvider extends FabricDynamicRegistryProvider {

        public RegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {

        }

        @Override
        public @NonNull String getName() {
            return "Registries";
        }
    }
}
