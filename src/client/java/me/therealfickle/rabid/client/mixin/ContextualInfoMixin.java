package me.therealfickle.rabid.client.mixin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Gui.ContextualInfo.class)
public enum ContextualInfoMixin {
    RABID_HEAT();

    @Shadow
    ContextualInfoMixin() {
    }
}
