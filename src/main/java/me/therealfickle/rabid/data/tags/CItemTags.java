package me.therealfickle.rabid.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static me.therealfickle.rabid.Rabid.id;

public interface CItemTags {

    TagKey<Item> CRATES = key("crates");
    TagKey<Item> STORAGE_BLOCK_SFA = key("storage_blocks/sfa");
    TagKey<Item> PLAYER_WORKSTATIONS_MATTER_RECONSTRUCTORS = key("player_workstations/matter_reconstructors");


    TagKey<Item> POLONIUM = key("polonium");
    TagKey<Item> POLONIUM_NUGGETS = key("nuggets/polonium");
    TagKey<Item> SFA_INGOTS = key("ingots/sfa");

    static TagKey<Item> key(String id) {
        return TagKey.create(Registries.ITEM, id("c", id));
    }

}
