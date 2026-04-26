package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(RabidBlocks.FICLIUM_BLOCK)
                .add(RabidBlocks.FICLIUM_CRATE)
                .add(RabidBlocks.POD_DISTRESS_LIGHT);
    }
}
