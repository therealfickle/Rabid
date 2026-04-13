package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.data.tags.RabidDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagsProvider extends FabricTagProvider<DamageType> {

    public DamageTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        builder(RabidDamageTypeTags.HELR_CALLER_IMMUNE)
                .forceAddTag(DamageTypeTags.IS_FIRE)
                .forceAddTag(DamageTypeTags.IS_EXPLOSION)
                .forceAddTag(DamageTypeTags.IS_LIGHTNING)
                .add(DamageTypes.CACTUS)
                .add(DamageTypes.FELL_OUT_OF_WORLD)
        ;
    }
}
