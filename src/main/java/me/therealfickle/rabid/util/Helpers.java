package me.therealfickle.rabid.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

public interface Helpers {
    String FICKLE_UUID = "0d16d93b-dc28-40d4-9104-b2b13da3ea53";

    static boolean isFickle(Player player) {
        return player.getUUID().toString().equals(FICKLE_UUID) || isDev();
    }

    static boolean isDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

}
