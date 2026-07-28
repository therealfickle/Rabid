package me.therealfickle.rabid.init;

import me.therealfickle.rabid.data.tags.RabidDamageTypeTags;
import me.therealfickle.rabid.init.misc.RabidToolMaterials;
import me.therealfickle.rabid.item.ExperimentalHELRCallerItem;
import me.therealfickle.rabid.item.GOTMEItem;
import me.therealfickle.rabid.item.QECItem;
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

    Item EXPERIMENTAL_HELR_CALLER = register("experimental_helr_caller", ExperimentalHELRCallerItem::new,
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(RabidDamageTypeTags.HELR_CALLER_IMMUNE))
    );
    Item QUANTUM_ENERGY_CANNON = register("quantum_energy_cannon", QECItem::new, new Properties());
    Item GOTME = register("gotme", GOTMEItem::new,
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
