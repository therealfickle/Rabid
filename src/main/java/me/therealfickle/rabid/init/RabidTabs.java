package me.therealfickle.rabid.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidTabs {
    @SuppressWarnings("unused")
    CreativeModeTab RABID_TAB = register("rabid_tab", FabricItemGroup.builder()
            .icon(() -> new ItemStack(RabidBlocks.FICKLE_PLUSH))
            .title(Component.translatable("itemGroup.rabid.rabid_tab"))
            .displayItems((parameters, output) -> {
                output.accept(RabidBlocks.FICKLE_PLUSH);
                output.accept(RabidItems.EXPERIMENTAL_HELR_CALLER);
                output.accept(RabidItems.GOTME);
                output.accept(RabidItems.QUANTUM_ENERGY_CANNON);
            })
            .build()
    );

    static void init() {
    }

    static CreativeModeTab register(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), tab);
    }
}
