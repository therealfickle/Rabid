package me.therealfickle.rabid.mixins;

import me.therealfickle.rabid.init.RabidAttachments;
import me.therealfickle.rabid.init.RabidDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodProperties.class)
public class FoodPropertiesMixin {

    @Inject(method = "onConsume", at = @At("HEAD"), cancellable = true)
    void canConsumeFuel(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable, CallbackInfo ci) {
        var fuel = itemStack.get(RabidDataComponents.FICKLE_FUEL);
        if (fuel == null) return;
        if (fuel.anyoneGainsFoodEffects()) return;
        if (!RabidAttachments.isInFickleMode(livingEntity)) {
            ci.cancel();
        }
    }
}
