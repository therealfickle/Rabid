package me.therealfickle.rabid;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.therealfickle.rabid.init.RabidAttachments;
import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import me.therealfickle.rabid.init.RabidItems;
import me.therealfickle.rabid.init.RabidTabs;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rabid implements ModInitializer {
    public static final String MODID = "rabid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static RabidConfig CONFIG = ConfigApiJava.registerAndLoadConfig(RabidConfig::new);

    @Override
    public void onInitialize() {
        RabidItems.init();
        RabidBlockEntityTypes.init();
        RabidTabs.init();
        RabidAttachments.init();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
