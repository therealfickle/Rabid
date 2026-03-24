package me.therealfickle.rabid.init;

import me.therealfickle.rabid.item.ExperimentalHELRCallerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidItems {
    FoodProperties PP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    Item POLONIUM_PELLET = register("polonium_pellet", new Item(new Item.Properties().food(PP)));
    FoodProperties PN = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
    Item POLONIUM_NUGGET = register("polonium_nugget", new Item(new Item.Properties().food(PN)));

    Item EXPERIMENTAL_HELR_CALLER = register("experimental_helr_caller", new ExperimentalHELRCallerItem(new Item.Properties()));

    static void init() {

    }

    static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, id(name), item);
    }
}
