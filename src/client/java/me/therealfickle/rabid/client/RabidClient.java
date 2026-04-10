package me.therealfickle.rabid.client;

import me.therealfickle.rabid.client.gui.HeatBarRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class RabidClient implements ClientModInitializer {

    public static HeatBarRenderer BAR;
    @Override
    public void onInitializeClient() {
        BAR = new HeatBarRenderer(Minecraft.getInstance());
    }
}
