package me.therealfickle.rabid.item;

import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExperimentalHELRCallerItem extends Item {
    public ExperimentalHELRCallerItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            var fickleMode = RabidAttachments.isInFickleMode(player);
            if (fickleMode) {
                player.displayClientMessage(Component.literal("Turning off fickle mode :C"), true);
            } else {
                player.displayClientMessage(Component.literal("Turning ON fickle mode :3"), true);
            }
            RabidAttachments.setFickleMode(player, !fickleMode);
            player.getCooldowns().addCooldown(this, 200);
        }
        return InteractionResultHolder.success(itemStack);
    }
}
