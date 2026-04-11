package me.therealfickle.rabid.item;

import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import static me.therealfickle.rabid.Rabid.CONFIG;
import static me.therealfickle.rabid.util.Helpers.isFickle;

public class ExperimentalHELRCallerItem extends Item {
    public ExperimentalHELRCallerItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        if (CONFIG.HELRCIsFickleOnly && !isFickle(player)) {
            player.displayClientMessage(Component.literal("No chuds allowed"), true);
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            var fickleMode = RabidAttachments.isInFickleMode(player);
            if (fickleMode) {
                player.displayClientMessage(Component.literal("Turning off fickle mode :C"), true);
            } else {
                player.displayClientMessage(Component.literal("Turning ON fickle mode :3"), true);
            }
            RabidAttachments.setFickleMode(player, !fickleMode);
            if (!player.isCreative()) player.getCooldowns().addCooldown(itemStack, 200);
        }
        return InteractionResult.SUCCESS;
    }
}
