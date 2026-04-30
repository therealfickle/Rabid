package me.therealfickle.rabid.client.mixin.appleskin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidAttachments;
import me.therealfickle.rabid.init.RabidDataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.helpers.FoodHelper;

@Pseudo
@Mixin(HUDOverlayHandler.HeldFoodCache.class)
public class HeldFoodCacheMixin {
    @WrapOperation(
            method = "query",
            at = @At(value = "INVOKE", target = "Lsqueek/appleskin/helpers/FoodHelper;query(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lsqueek/appleskin/helpers/FoodHelper$QueriedFoodResult;")
    )
    private static FoodHelper.QueriedFoodResult modifyQuery(ItemStack itemStack, Player player, Operation<FoodHelper.QueriedFoodResult> original) {
        var originalQuery = original.call(itemStack, player);

        var fuel = itemStack.get(RabidDataComponents.FICKLE_FUEL);
        boolean hasPowers = RabidAttachments.isInFickleMode(player);

        if (fuel != null) {
            if ((fuel.anyoneCanEat() && fuel.anyoneGainsFoodEffects()) || hasPowers) {
                return originalQuery;
            }
        } else if (hasPowers) {
            return null;
        }

        return originalQuery;
    }

    @WrapOperation(
            method = "query",
            at = @At(value = "INVOKE", target = "Lsqueek/appleskin/helpers/FoodHelper;canConsume(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/food/FoodProperties;)Z")
    )
    private static boolean modifyCanConsume(Player player, FoodProperties foodComponent, Operation<Boolean> original, @Local ItemStack itemStack) {
        var originalCanEat = original.call(player, foodComponent);

        var fuel = itemStack.get(RabidDataComponents.FICKLE_FUEL);
        boolean hasPowers = RabidAttachments.isInFickleMode(player);

        if (fuel != null) {
            return ((fuel.anyoneCanEat() && fuel.anyoneGainsFoodEffects()) || hasPowers) && originalCanEat;
        }

        if (hasPowers) {
            return itemStack.is(RabidItemTags.ADDITIONAL_FICKLE_FUELS) && originalCanEat;
        }

        return originalCanEat;
    }
}
