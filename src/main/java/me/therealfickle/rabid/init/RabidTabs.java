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
                output.accept(RabidBlocks.MATTER_RECONSTRUCTOR);
                output.accept(RabidItems.POLONIUM_PELLET);
                output.accept(RabidItems.POLONIUM_NUGGET);
                output.accept(RabidItems.FABRICATED_INTEGRATED_COMPONENT);
                output.accept(RabidItems.SFA_INGOT);
                output.accept(RabidBlocks.SFA_BLOCK);
                output.accept(RabidBlocks.SFA_CRATE);
                output.accept(RabidBlocks.SFA_DISTRESS_LIGHT);
                output.accept(RabidItems.GOTME);
                output.accept(RabidItems.HERACLES);
            })
            .build()
    );

    static void init() {
    }

    static CreativeModeTab register(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), tab);
    }
}
