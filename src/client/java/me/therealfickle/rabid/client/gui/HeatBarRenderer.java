package me.therealfickle.rabid.client.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import org.jspecify.annotations.NonNull;

public class HeatBarRenderer implements ContextualBarRenderer {

    private final Minecraft minecraft;

    public HeatBarRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, @NonNull DeltaTracker deltaTracker) {
        LocalPlayer localPlayer = minecraft.player;
        assert localPlayer != null;
        int x = left(minecraft.getWindow());
        int y = top(minecraft.getWindow());

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RabidSprites.HEAT_BAR_BACKGROUND, x, y, 182, 5);
        var heatFillLevel = 0.5f; // todo change to sample real values
        int progress = (int) (heatFillLevel * 183.0F);
        //noinspection ConstantValue
        if (progress > 0) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RabidSprites.HEAT_BAR_PROGRESS, 182, 5, 0, 0, x, y, progress, 5);
        }
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, @NonNull DeltaTracker deltaTracker) {
    }
}
