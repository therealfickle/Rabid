package me.therealfickle.rabid.init;

import me.therealfickle.rabid.data.tags.RabidDamageTypeTags;
import me.therealfickle.rabid.init.misc.RabidItemData;
import me.therealfickle.rabid.init.misc.RabidToolMaterials;
import me.therealfickle.rabid.item.ExperimentalHELRCallerItem;
import me.therealfickle.rabid.item.HeraclesItem;
import me.therealfickle.rabid.item.SFAGlaiveItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.DamageResistant;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;
import static me.therealfickle.rabid.init.misc.RabidToolMaterials.applyGlaiveProperties;

public interface RabidItems {
    Item POLONIUM_PELLET = register("polonium_pellet",
            new Properties()
                    .food(RabidItemData.POLONIUM_PELLET)
                    .component(DataComponents.CONSUMABLE, RabidItemData.FUEL_CONSUMABLE)
                    .component(RabidDataComponents.FICKLE_FUEL, RabidItemData.FUEL)
    );

    Item POLONIUM_NUGGET = register("polonium_nugget",
            new Properties()
                    .food(RabidItemData.POLONIUM_NUGGET)
                    .component(DataComponents.CONSUMABLE, RabidItemData.FUEL_CONSUMABLE)
                    .component(RabidDataComponents.FICKLE_FUEL, RabidItemData.FUEL)
    );

    Item SFA_INGOT = register("sfa_ingot", new Properties());

    Item EXPERIMENTAL_HELR_CALLER = register("experimental_helr_caller", ExperimentalHELRCallerItem::new,
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(RabidDamageTypeTags.HELR_CALLER_IMMUNE))
    );

    Item PIPE_BOMB = register("pipe_bomb", new Properties());
    Item HERACLES = register("heracles", HeraclesItem::new, new Properties());
    Item SFA_GLAIVE = register("sfa_glaive", SFAGlaiveItem::new,
            applyGlaiveProperties(new Properties(), RabidToolMaterials.SFA, 3.0F, -2.8F)
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
