package me.therealfickle.rabid.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import me.therealfickle.rabid.client.RabidSprites;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    @Final
    private static ResourceLocation FOOD_EMPTY_HUNGER_SPRITE;

    @Shadow
    @Final
    private static ResourceLocation FOOD_HALF_SPRITE;

    @Shadow
    @Final
    private static ResourceLocation FOOD_FULL_SPRITE;

    @Shadow
    @Final
    private static ResourceLocation FOOD_EMPTY_SPRITE;

    @Shadow
    @Final
    private static ResourceLocation FOOD_FULL_HUNGER_SPRITE;

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    ResourceLocation x(ResourceLocation resourceLocation, @Local(argsOnly = true) Player player) {
        var fickleMode = RabidAttachments.isInFickleMode(player);
        if (fickleMode) {
            if (resourceLocation.equals(FOOD_EMPTY_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL_EMPTY;
            }
            if (resourceLocation.equals(FOOD_EMPTY_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL_HALF;
            }
            if (resourceLocation.equals(FOOD_FULL_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL;
            }
            if (resourceLocation.equals(FOOD_EMPTY_SPRITE)) {
                return RabidSprites.FUEL_EMPTY;
            }
            if (resourceLocation.equals(FOOD_HALF_SPRITE)) {
                return RabidSprites.FUEL_HALF;
            }
            if (resourceLocation.equals(FOOD_FULL_SPRITE)) {
                return RabidSprites.FUEL;
            }
        }

        return resourceLocation;
    }

    @ModifyReturnValue(method = "isExperienceBarVisible", at = @At("RETURN"))
    boolean y(boolean original) {
        var fickleMode = RabidAttachments.isInFickleMode(minecraft.player);
        if (fickleMode) {
            return false;
        }

        return original;
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;canHurtPlayer()Z"))
    void z(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        var fickleMode = RabidAttachments.isInFickleMode(minecraft.player);
        if (fickleMode) {
            this.minecraft.getProfiler().push("heatBar");
            int i = guiGraphics.guiWidth() / 2 - 91;
            int m = guiGraphics.guiHeight() - 32 + 3;
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(RabidSprites.HEAT_BAR, i, m, 182, 5);

            RenderSystem.disableBlend();

            this.minecraft.getProfiler().pop();
        }
    }

}
