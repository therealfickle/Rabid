package me.therealfickle.rabid.init;

import me.therealfickle.rabid.data.tags.RabidDamageTypeTags;
import me.therealfickle.rabid.item.ExperimentalHELRCallerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DamageResistant;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidItems {
    FoodProperties PP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    Item POLONIUM_PELLET = register("polonium_pellet", new Item.Properties().food(PP));
    FoodProperties PN = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
    Item POLONIUM_NUGGET = register("polonium_nugget", new Item.Properties().food(PN));

    Item EXPERIMENTAL_HELR_CALLER = register("experimental_helr_caller", ExperimentalHELRCallerItem::new,
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(RabidDamageTypeTags.HELR_CALLER_IMMUNE))
    );

    static void init() {

    }


    static Item register(String name, Item.Properties properties) {
        return register(name, Item::new, properties);
    }

    static Item register(String name, Function<Item.Properties, Item> function, Item.Properties properties) {
        var key = ResourceKey.create(Registries.ITEM, id(name));
        var item = function.apply(properties.setId(key));

        return Registry.register(BuiltInRegistries.ITEM, key.identifier(), item);
    }
}
