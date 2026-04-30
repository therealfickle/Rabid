package me.therealfickle.rabid.init.misc;

import me.therealfickle.rabid.fickle.FickleFuel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;

public interface RabidItemData {

    FickleFuel FUEL = new FickleFuel.Builder().build();

    FoodProperties POLONIUM_PELLET = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .build();

    FoodProperties POLONIUM_NUGGET = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.1F)
            .build();

    Consumable FUEL_CONSUMABLE = Consumable.builder()
            .consumeSeconds(0.8F)
            .animation(ItemUseAnimation.EAT)
            .sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.GILDED_BLACKSTONE_BREAK))
            .hasConsumeParticles(true)
            .build();


}
