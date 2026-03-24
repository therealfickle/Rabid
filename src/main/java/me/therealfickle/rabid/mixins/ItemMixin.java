package me.therealfickle.rabid.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class ItemMixin {
    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
    Object f(Object food, Level level, Player player, InteractionHand interactionHand, @Local ItemStack itemStack) {
        var fickleMode = RabidAttachments.isInFickleMode(player);
        if (fickleMode) {
            if (itemStack.is(RabidItemTags.FICKLE_FUELS)) {
                return food;
            }
            return null;
        }
        if (itemStack.is(RabidItemTags.INEDIBLE)) {
            return null;
        }


        return food;
    }
}
