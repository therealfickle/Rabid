package me.therealfickle.rabid.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;

import static me.therealfickle.rabid.Rabid.id;

@SuppressWarnings("UnstableApiUsage")
public interface RabidAttachments {
    AttachmentType<Boolean> FICKLE_MODE = AttachmentRegistry.create(id("fickle_mode"), (builder) ->
            builder.persistent(Codec.BOOL)
                    .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
                    .initializer(() -> false)
                    .copyOnDeath()
    );

    static boolean isInFickleMode(Player player) {
        return player.getAttachedOrCreate(FICKLE_MODE);
    }

    static void setFickleMode(Player player, boolean value) {
        player.setAttached(FICKLE_MODE, value);
    }

    static void init() {

    }
}
