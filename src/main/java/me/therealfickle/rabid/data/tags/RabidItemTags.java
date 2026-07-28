package me.therealfickle.rabid.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidItemTags {

    TagKey<Item> SFA_TOOL_MATERIALS = key("sfa_tool_materials");

    static TagKey<Item> key(String id) {
        return TagKey.create(Registries.ITEM, id(id));
    }
}
