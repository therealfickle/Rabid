package me.therealfickle.rabid.item;

import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ExperimentalHELRCallerItem extends Item {
    public ExperimentalHELRCallerItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
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
