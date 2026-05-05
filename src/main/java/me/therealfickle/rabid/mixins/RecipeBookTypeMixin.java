package me.therealfickle.rabid.mixins;

import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecipeBookType.class)
public enum RecipeBookTypeMixin {

    RABID_RECONSTRUCTING();

    @Shadow
    RecipeBookTypeMixin() {
    }

}
