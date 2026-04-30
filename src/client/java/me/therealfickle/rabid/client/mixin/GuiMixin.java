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
    private Minecraft minecraft;

    @Shadow
    protected abstract boolean willPrioritizeJumpInfo();

    @Mutable
    @Shadow
    @Final
    private Map<Gui.ContextualInfo, Supplier<ContextualBarRenderer>> contextualInfoBarRenderers;

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    Identifier changeFoodSprites(Identifier identifier, @Local(argsOnly = true) Player player) {
        return RabidAttachments.isInFickleMode(player) ? RabidSprites.getFuelTexture(identifier) : identifier;
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
