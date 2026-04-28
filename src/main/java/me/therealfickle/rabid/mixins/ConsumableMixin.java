package me.therealfickle.rabid.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidAttachments;
import me.therealfickle.rabid.init.RabidDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Consumable.class)
public class ConsumableMixin {

    @ModifyReturnValue(method = "canConsume", at = @At("RETURN"))
    boolean canConsumeFuel(boolean original, LivingEntity livingEntity, ItemStack itemStack) {
        if (!(livingEntity instanceof Player player)) return original;

        var fuel = itemStack.get(RabidDataComponents.FICKLE_FUEL);
        var isFuel = fuel != null;
        var isAdditionalFuel = itemStack.is(RabidItemTags.ADDITIONAL_FICKLE_FUELS);

        if (RabidAttachments.isInFickleMode(player)) {
            return ((isFuel && player.canEat(fuel.canAlwaysConsume())) || isAdditionalFuel) && original;
        }

        return !isFuel && original;

    }
}
