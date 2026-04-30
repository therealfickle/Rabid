package me.therealfickle.rabid.client.mixin.appleskin;

import com.llamalad7.mixinextras.sugar.Local;
import me.therealfickle.rabid.client.gui.RabidSprites;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import squeek.appleskin.client.HUDOverlayHandler;

@Pseudo
@Mixin(HUDOverlayHandler.class)
public class HUDOverlayHandlerMixin {

    @ModifyArg(
            method = "drawSaturationOverlay(Lnet/minecraft/client/gui/GuiGraphics;FFLnet/minecraft/client/Minecraft;IIFI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V")
    )
    Identifier modifySaturationTexture(Identifier texture, @Local(argsOnly = true) Minecraft mc) {
        return RabidAttachments.isInFickleMode(mc.player) ? RabidSprites.APPLESKIN_SPRITES : texture;
    }



    @ModifyArg(
            method = "drawHungerOverlay(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/Minecraft;IIFZI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V")
    )
    Identifier modifyHungerTexture(Identifier texture, @Local(argsOnly = true) Minecraft mc) {
        return RabidAttachments.isInFickleMode(mc.player) ? RabidSprites.getFuelTexture(texture, null) : texture;
    }

}