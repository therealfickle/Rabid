package me.therealfickle.rabid.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

//    @Inject(method = "tick", at = @At("HEAD"))
//    void clearHunger(CallbackInfo ci) {
//        if (CONFIG.fickleImmuneToHunger && RabidAttachments.isInFickleMode(this) && hasEffect(MobEffects.HUNGER)) {
//            removeEffect(MobEffects.HUNGER);
//        }
//    }
}
