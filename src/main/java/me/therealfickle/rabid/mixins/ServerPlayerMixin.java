package me.therealfickle.rabid.mixins;

import com.mojang.authlib.GameProfile;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.therealfickle.rabid.Rabid.CONFIG;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    void clearHunger(CallbackInfo ci) {
        if (CONFIG.fickleImmuneToHunger && RabidAttachments.isInFickleMode(this) && hasEffect(MobEffects.HUNGER)) {
            removeEffect(MobEffects.HUNGER);
        }
    }
}
