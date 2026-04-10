package me.therealfickle.rabid.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.therealfickle.rabid.client.RabidClient;
import me.therealfickle.rabid.client.gui.RabidSprites;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    @Final
    private static Identifier FOOD_EMPTY_HUNGER_SPRITE;

    @Shadow
    @Final
    private static Identifier FOOD_HALF_SPRITE;

    @Shadow
    @Final
    private static Identifier FOOD_FULL_SPRITE;

    @Shadow
    @Final
    private static Identifier FOOD_EMPTY_SPRITE;

    @Shadow
    @Final
    private static Identifier FOOD_FULL_HUNGER_SPRITE;

    @Shadow
    @Final
    private static Identifier FOOD_HALF_HUNGER_SPRITE;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract boolean willPrioritizeJumpInfo();

    @Mutable
    @Shadow
    @Final
    private Map<Gui.ContextualInfo, Supplier<ContextualBarRenderer>> contextualInfoBarRenderers;

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    Identifier changeFoodSprites(Identifier identifier, @Local(argsOnly = true) Player player) {
        var fickleMode = RabidAttachments.isInFickleMode(player);
        if (fickleMode) {
            if (identifier.equals(FOOD_EMPTY_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL_EMPTY;
            }
            if (identifier.equals(FOOD_HALF_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL_HALF;
            }
            if (identifier.equals(FOOD_FULL_HUNGER_SPRITE)) {
                return RabidSprites.HUNGER_FUEL;
            }
            if (identifier.equals(FOOD_EMPTY_SPRITE)) {
                return RabidSprites.FUEL_EMPTY;
            }
            if (identifier.equals(FOOD_HALF_SPRITE)) {
                return RabidSprites.FUEL_HALF;
            }
            if (identifier.equals(FOOD_FULL_SPRITE)) {
                return RabidSprites.FUEL;
            }
        }

        return identifier;
    }

    @Inject(method = "nextContextualInfoState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"), cancellable = true)
    void renderHeatBar(CallbackInfoReturnable<Gui.ContextualInfo> cir) {
        if (RabidAttachments.isInFickleMode(minecraft.player) && !willPrioritizeJumpInfo()) {
            cir.setReturnValue(Gui.ContextualInfo.RABID_HEAT);
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void modifyList(Minecraft minecraft, CallbackInfo ci) {
        var map = new HashMap<>(contextualInfoBarRenderers);
        map.put(Gui.ContextualInfo.RABID_HEAT, () -> RabidClient.BAR);
        contextualInfoBarRenderers = map;
    }

}
