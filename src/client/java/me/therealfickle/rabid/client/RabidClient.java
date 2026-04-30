package me.therealfickle.rabid.client;

import me.therealfickle.rabid.client.gui.HeatBarRenderer;
import me.therealfickle.rabid.client.gui.screens.inventory.MatterReconstructorScreen;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidDataComponents;
import me.therealfickle.rabid.init.RabidMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.item.ItemStack;

public class RabidClient implements ClientModInitializer {

    public static HeatBarRenderer BAR;

    @Override
    public void onInitializeClient() {
        BAR = new HeatBarRenderer(Minecraft.getInstance());
        BlockRenderLayerMap.putBlock(RabidBlocks.FICKLE_PLUSH, ChunkSectionLayer.CUTOUT);
        MenuScreens.register(RabidMenuTypes.MATTER_RECONSTRUCTOR, MatterReconstructorScreen::new);
    }

    public static boolean renderAsFuel(ItemStack itemStack) {
        return itemStack.get(RabidDataComponents.FICKLE_FUEL) != null;
    }
}
