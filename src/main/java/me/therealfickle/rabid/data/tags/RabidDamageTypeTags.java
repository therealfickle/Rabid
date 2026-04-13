package me.therealfickle.rabid.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidDamageTypeTags {
    TagKey<DamageType> HELR_CALLER_IMMUNE = key("helr_caller_immune");

    static TagKey<DamageType> key(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, id(id));
    }
}
