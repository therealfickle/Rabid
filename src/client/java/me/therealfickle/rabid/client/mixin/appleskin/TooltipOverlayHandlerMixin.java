package me.therealfickle.rabid.client.mixin.appleskin;

import com.llamalad7.mixinextras.sugar.Local;
import me.therealfickle.rabid.client.gui.RabidSprites;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import squeek.appleskin.client.TooltipOverlayHandler;

import static me.therealfickle.rabid.client.RabidClient.renderAsFuel;
import static me.therealfickle.rabid.client.gui.RabidSprites.getFuelTexture;

@Pseudo
@Mixin(TooltipOverlayHandler.class)
public class TooltipOverlayHandlerMixin {

    @ModifyArg(
            method = "onRenderTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V")
    )
    Identifier modifyFoodIcons1(Identifier texture, @Local ItemStack itemStack) {
        return getFuelTexture(texture, itemStack);
    }

    @ModifyArg(
            method = "onRenderTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V")
    )
    Identifier modifyFoodIcons2(Identifier texture, @Local ItemStack itemStack) {
        return getFuelTexture(texture, itemStack);
    }

    @ModifyArg(
            method = "onRenderTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V")
    )
    Identifier modifyModIcons(Identifier texture, @Local ItemStack itemStack) {
        return renderAsFuel(itemStack) ? RabidSprites.APPLESKIN_SPRITES : texture;
    }

}