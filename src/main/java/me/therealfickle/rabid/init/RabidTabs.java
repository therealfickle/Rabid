package me.therealfickle.rabid.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidTabs {
    CreativeModeTab RABID_TAB = register("rabid_tab", FabricItemGroup.builder()
            .icon(() -> new ItemStack(RabidItems.EXPERIMENTAL_HELR_CALLER))
            .title(Component.translatable("itemGroup.rabid.rabid_tab"))
            .displayItems((parameters, output) -> {
                output.accept(RabidItems.EXPERIMENTAL_HELR_CALLER);
                output.accept(RabidItems.POLONIUM_PELLET);
                output.accept(RabidItems.POLONIUM_NUGGET);
            })
            .build()
    );

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register((output) -> {
            output.addAfter(Items.DIAMOND, RabidItems.EXPERIMENTAL_HELR_CALLER);
        });
    }

    static CreativeModeTab register(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), tab);
    }
}
