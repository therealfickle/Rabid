package me.therealfickle.rabid.mixins;

import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.UnaryOperator;

@Mixin(RecipeBookSettings.class)
public abstract class RecipeBookSettingsMixin {

    @Shadow
    private RecipeBookSettings.TypeSettings furnace;

    @Inject(method = "getSettings", at = @At("HEAD"), cancellable = true)
    void fixCrashPart1(RecipeBookType recipeBookType, CallbackInfoReturnable<RecipeBookSettings.TypeSettings> cir) {
        if (recipeBookType == RecipeBookType.RABID_RECONSTRUCTING) {
            cir.setReturnValue(this.furnace);
        }
    }


    @Inject(method = "updateSettings", at = @At("HEAD"), cancellable = true)
    void fixCrashPart2(RecipeBookType recipeBookType, UnaryOperator<RecipeBookSettings.TypeSettings> unaryOperator, CallbackInfo ci) {
        if (recipeBookType == RecipeBookType.RABID_RECONSTRUCTING) {
            furnace = unaryOperator.apply(furnace);
            ci.cancel();
        }
    }
}
