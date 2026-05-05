package me.therealfickle.rabid.client.gui.screens.inventory;

import me.therealfickle.rabid.client.gui.screens.MatterReconstructorRecipeBookComponent;
import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.NonInteractiveResultSlot;
import net.minecraft.world.inventory.Slot;

import static me.therealfickle.rabid.Rabid.CONFIG;
import static me.therealfickle.rabid.Rabid.id;

@Environment(EnvType.CLIENT)
public class MatterReconstructorScreen extends AbstractRecipeBookScreen<MatterReconstructorMenu> {
    private static final Identifier FUEL_FULL = id("container/matter_reconstructor/fuel_full");
    private static final Identifier DISABLED_OUTPUT = id("container/matter_reconstructor/disabled_output");
    private static final Identifier LIGHT_GREEN = id("container/matter_reconstructor/light_green");
    private static final Identifier LIGHT_RED = id("container/matter_reconstructor/light_red");
    private static final Identifier CONTAINER_TEXTURE = id("textures/gui/container/matter_reconstructor.png");

    public MatterReconstructorScreen(MatterReconstructorMenu menu, Inventory inventory, Component component) {
        super(menu, new MatterReconstructorRecipeBookComponent(menu), inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(leftPos + 24, height / 2 - 30);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float tickDelta) {
        super.render(guiGraphics, x, y, tickDelta);
        renderLight(guiGraphics);
        renderFuel(guiGraphics);
        renderTooltip(guiGraphics, x, y);
    }

    private void renderFuel(GuiGraphics guiGraphics) {
        int x = leftPos + 25;
        int y = height / 2 - 55;

        var sWidth = 18;
        var max = (int) CONFIG.matterReconstructor.maxFuelStorage.get();

        int fuelLevel = menu.getFuel();
        int drawAmount = Mth.clamp((sWidth * fuelLevel + max - 1) / max, 0, sWidth);
        if (drawAmount > 0) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FUEL_FULL, 18, 4, 0, 0, x, y, drawAmount, 4);
        }
    }

    private void renderLight(GuiGraphics guiGraphics) {
        if (!menu.isPowered()) return;
        int x = leftPos + 138;
        int y = height / 2 - 22;
        Identifier tex = menu.hasResult() ? LIGHT_GREEN : LIGHT_RED;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, tex, x, y, 8, 8);
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot, int x, int y) {
        if (menu.isPowered() && !menu.hasResult() && slot instanceof NonInteractiveResultSlot) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, DISABLED_OUTPUT, slot.x - 1, slot.y - 1, 18, 18);
        } else {
            super.renderSlot(guiGraphics, slot, x, y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = leftPos;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, x, y, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
        var text = "Assembly Time: %3.1fs".formatted(menu.getAssemblyTime() / 20.0);
        guiGraphics.drawString(font, text, (width / 2) - font.width(text) / 2, y - 10, 0xff_f0f0f0);
    }
}
