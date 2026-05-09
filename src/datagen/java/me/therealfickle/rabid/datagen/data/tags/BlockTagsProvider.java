package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.data.tags.CBlockTags;
import me.therealfickle.rabid.init.RabidBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
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
                .add(RabidBlocks.MATTER_RECONSTRUCTOR)
                .add(RabidBlocks.SFA_BLOCK)
                .add(RabidBlocks.SFA_CRATE)
                .add(RabidBlocks.SFA_DISTRESS_LIGHT)
        ;

        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(RabidBlocks.SFA_BLOCK)
                .add(RabidBlocks.SFA_CRATE)
                .add(RabidBlocks.SFA_DISTRESS_LIGHT)
        ;

        valueLookupBuilder(BlockTags.BEACON_BASE_BLOCKS).add(RabidBlocks.SFA_BLOCK);
        valueLookupBuilder(BlockTags.FEATURES_CANNOT_REPLACE).add(RabidBlocks.SFA_CRATE);


        // Common Tags
        valueLookupBuilder(CBlockTags.STORAGE_BLOCK_SFA).add(RabidBlocks.SFA_BLOCK);
        valueLookupBuilder(ConventionalBlockTags.STORAGE_BLOCKS).forceAddTag(CBlockTags.STORAGE_BLOCK_SFA);

        valueLookupBuilder(CBlockTags.CRATES).add(RabidBlocks.SFA_CRATE);

        valueLookupBuilder(CBlockTags.PLAYER_WORKSTATIONS_MATTER_RECONSTRUCTORS).add(RabidBlocks.MATTER_RECONSTRUCTOR);

    }
}
