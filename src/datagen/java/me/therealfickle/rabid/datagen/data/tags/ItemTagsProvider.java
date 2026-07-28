package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BlockTagProvider blockTags) {
        super(output, registriesFuture, blockTags);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(RabidItems.GOTME);
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPONS_TOOLS).add(RabidItems.GOTME);
        valueLookupBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS).add(RabidItems.QUANTUM_ENERGY_CANNON);
        valueLookupBuilder(ConventionalItemTags.RANGED_WEAPONS_TOOLS).add(RabidItems.QUANTUM_ENERGY_CANNON);
    }
}
