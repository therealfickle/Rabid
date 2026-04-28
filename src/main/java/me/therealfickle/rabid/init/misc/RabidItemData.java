package me.therealfickle.rabid.init.misc;

import me.therealfickle.rabid.fickle.FickleFuel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;

public interface RabidItemData {

    FickleFuel POLONIUM_PELLET = new FickleFuel.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .build();
    FickleFuel POLONIUM_NUGGET = new FickleFuel.Builder()
            .nutrition(1)
            .saturationModifier(0.1F)
            .build();

    Consumable FUEL_CONSUMABLE = Consumable.builder()
            .consumeSeconds(1.6F)
            .animation(ItemUseAnimation.EAT)
            .sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.GILDED_BLACKSTONE_BREAK))
            .hasConsumeParticles(true)
            .build();


}
