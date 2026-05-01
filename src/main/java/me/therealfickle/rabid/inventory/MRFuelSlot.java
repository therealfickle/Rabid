package me.therealfickle.rabid.inventory;

import me.therealfickle.rabid.data.tags.RabidItemTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static me.therealfickle.rabid.Rabid.id;

class MRFuelSlot extends Slot {
    static final Identifier EMPTY_FUEL_SLOT = id("container/slot/matter_reconstructor_fuel");
    public MRFuelSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return mayPlaceItem(itemStack);
    }

    public static boolean mayPlaceItem(ItemStack itemStack) {
        return itemStack.is(RabidItemTags.MATTER_RECONSTRUCTOR_FUELS);
    }

    @Override
    public Identifier getNoItemIcon() {
        return EMPTY_FUEL_SLOT;
    }
}