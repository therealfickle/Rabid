package me.therealfickle.rabid;

import com.mojang.brigadier.context.CommandContext;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.therealfickle.rabid.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.therealfickle.rabid.util.Helpers.isFickle;
import static net.minecraft.commands.Commands.literal;

public class Rabid implements ModInitializer {
    public static final String MODID = "rabid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static RabidConfig CONFIG = ConfigApiJava.registerAndLoadConfig(RabidConfig::new);

    @Override
    public void onInitialize() {
        RabidItems.init();
        RabidBlocks.init();
        RabidBlockEntityTypes.init();
        RabidTabs.init();
        RabidAttachments.init();
        RabidStats.init();
        RabidSoundEvents.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, globalCtx, env) -> dispatcher.getRoot().addChild(
                literal("fickle")
                        .executes(this::fickleSpawnCommand)
                        .build()
        ));
    }

    private int fickleSpawnCommand(CommandContext<CommandSourceStack> ctx) {
        var src = ctx.getSource();
        var player = src.getPlayer();
        if (player == null) {
            src.sendFailure(Component.literal("Can only be run by a player!"));
            return 0;
        }
        if (!isFickle(player)) {
            src.sendFailure(Component.literal("Can only be run by Fickle!"));
            return 0;
        }

        var stack = RabidItems.EXPERIMENTAL_HELR_CALLER.getDefaultInstance();
        if (player.getInventory().contains(stack)) {
            src.sendFailure(Component.literal("Cant create more then one item!"));
            return 0;
        }

        for (var item : player.getEnderChestInventory().items) {
            if (item.is(RabidItems.EXPERIMENTAL_HELR_CALLER)) {
                src.sendFailure(Component.literal("Cant create more then one item!"));
                return 0;
            }
        }

        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
        src.sendSuccess(() -> Component.literal("Tv remote given!"), true);

        return 1;
    }

    public static Identifier id(String path) {
        return id(MODID, path);
    }

    public static Identifier id(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}
