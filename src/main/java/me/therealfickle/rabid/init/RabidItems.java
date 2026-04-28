package me.therealfickle.rabid.init;

import me.therealfickle.rabid.data.tags.RabidDamageTypeTags;
import me.therealfickle.rabid.init.misc.RabidItemData;
import me.therealfickle.rabid.item.ExperimentalHELRCallerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DamageResistant;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidItems {
    Item POLONIUM_PELLET = register("polonium_pellet",
            new Item.Properties()
                    .component(DataComponents.CONSUMABLE, RabidItemData.FUEL_CONSUMABLE)
                    .component(RabidDataComponents.FICKLE_FUEL, RabidItemData.POLONIUM_PELLET)
    );
    Item POLONIUM_NUGGET = register("polonium_nugget",
            new Item.Properties()
                    .component(DataComponents.CONSUMABLE, RabidItemData.FUEL_CONSUMABLE)
                    .component(RabidDataComponents.FICKLE_FUEL, RabidItemData.POLONIUM_NUGGET)
    );

    Item FICLIUM_INGOT = register("ficlium_ingot", new Item.Properties());

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
