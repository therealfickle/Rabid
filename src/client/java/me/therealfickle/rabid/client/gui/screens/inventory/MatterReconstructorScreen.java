package me.therealfickle.rabid.client.gui.screens.inventory;

import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import static me.therealfickle.rabid.Rabid.id;

@Environment(EnvType.CLIENT)
public class MatterReconstructorScreen extends AbstractContainerScreen<MatterReconstructorMenu> {
    private static final Identifier FUEL_FULL = id("container/matter_reconstructor/fuel_full");
    private static final Identifier LIGHT_GREEN = id("container/matter_reconstructor/light_green");
    private static final Identifier LIGHT_RED = id("container/matter_reconstructor/light_red");
    private static final Identifier CONTAINER_TEXTURE = id("textures/gui/container/matter_reconstructor.png");
    private final Player player;

    public MatterReconstructorScreen(MatterReconstructorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        player = inventory.player;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }


    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        renderLight(guiGraphics);
        renderTooltip(guiGraphics, i, j);
    }

    private void renderLight(GuiGraphics guiGraphics) {
        int x = width / 2 + 50;
        int y = height / 2 - 22;
        Identifier tex = menu.getResultItem().isEmpty() ? LIGHT_RED : LIGHT_GREEN;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, tex, x, y, 8, 8);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = (width - imageWidth) / 2;
        int l = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, x, l, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
    }
}
