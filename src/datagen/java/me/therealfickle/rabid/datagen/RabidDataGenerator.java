package me.therealfickle.rabid.datagen;

import me.therealfickle.rabid.Rabid;
import me.therealfickle.rabid.datagen.assets.ModelProvider;
import me.therealfickle.rabid.datagen.data.tags.BlockTagsProvider;
import me.therealfickle.rabid.datagen.data.tags.DamageTypeTagsProvider;
import me.therealfickle.rabid.datagen.data.tags.ItemTagsProvider;
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
        var pack = fabricDataGenerator.createPack();
        // Assets
        pack.addProvider(ModelProvider::new);
        // Data
        pack.addProvider(RegistryProvider::new);
        // Tags
        var blockTags = pack.addProvider(BlockTagsProvider::new);
        pack.addProvider((output, provider) -> new ItemTagsProvider(output, provider, blockTags));
        pack.addProvider(DamageTypeTagsProvider::new);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Rabid.MODID;
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder builder) {
    }

    static class RegistryProvider extends FabricDynamicRegistryProvider {

        public RegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.@NonNull Provider provider, @NonNull Entries entries) {
        }

        @Override
        public @NonNull String getName() {
            return "Registries";
        }
    }
}
