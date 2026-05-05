package me.therealfickle.rabid.client.mixin;

import net.minecraft.util.context.ContextMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.client.gui.screens.recipebook.GhostSlots")
public interface GhostSlotsAccessor {
    @Invoker("setResult")
    void rabid_setResult(Slot slot, ContextMap contextMap, SlotDisplay slotDisplay);

    @Invoker("setInput")
    void rabid_setInput(Slot slot, ContextMap contextMap, SlotDisplay slotDisplay);
}
