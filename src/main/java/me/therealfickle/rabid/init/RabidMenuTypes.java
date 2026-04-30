package me.therealfickle.rabid.init;

import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidMenuTypes {

    MenuType<MatterReconstructorMenu> MATTER_RECONSTRUCTOR = register("matter_reconstructor", MatterReconstructorMenu::new);

    static void init() {
    }

    static <T extends AbstractContainerMenu> MenuType<T> register(String string, MenuType.MenuSupplier<T> menuSupplier) {
        return Registry.register(BuiltInRegistries.MENU, id(string), new MenuType<>(menuSupplier, FeatureFlags.VANILLA_SET));
    }

}
