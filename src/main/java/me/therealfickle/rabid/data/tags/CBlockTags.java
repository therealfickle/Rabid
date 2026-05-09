package me.therealfickle.rabid.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static me.therealfickle.rabid.Rabid.id;

public interface CBlockTags {

    TagKey<Block> CRATES = key("crates");
    TagKey<Block> STORAGE_BLOCK_SFA = key("storage_blocks/sfa");
    TagKey<Block> PLAYER_WORKSTATIONS_MATTER_RECONSTRUCTORS = key("player_workstations/matter_reconstructors");



    static TagKey<Block> key(String id) {
        return TagKey.create(Registries.BLOCK, id("c", id));
    }

}
