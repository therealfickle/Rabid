package me.therealfickle.rabid.fickle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.level.Level;

public record FickleFuel(int nutrition, float saturationModifier, boolean canAlwaysConsume) implements ConsumableListener {
    public static final Codec<FickleFuel> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("nutrition").forGetter(FickleFuel::nutrition),
                            Codec.FLOAT.fieldOf("saturation_modifier").forGetter(FickleFuel::saturationModifier),
                            Codec.BOOL.optionalFieldOf("can_always_consume", false).forGetter(FickleFuel::canAlwaysConsume)
                    )
                    .apply(instance, FickleFuel::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, FickleFuel> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            FickleFuel::nutrition,
            ByteBufCodecs.FLOAT,
            FickleFuel::saturationModifier,
            ByteBufCodecs.BOOL,
            FickleFuel::canAlwaysConsume,
            FickleFuel::new
    );

    @Override
    public void onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable) {
        if (!(livingEntity instanceof Player player)) return;
        if (!RabidAttachments.isInFickleMode(player)) return;

        RandomSource randomSource = livingEntity.getRandom();
        level.playSound(
                null,
                livingEntity.getX(),
                livingEntity.getY(),
                livingEntity.getZ(),
                consumable.sound().value(),
                SoundSource.NEUTRAL,
                1.0F,
                randomSource.triangle(1.0F, 0.4F)
        );
        player.getFoodData().eat(nutrition, saturationModifier);
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private int nutrition;
        private float saturationModifier;
        private boolean canAlwaysConsume;

        public FickleFuel.Builder nutrition(int i) {
            nutrition = i;
            return this;
        }

        public FickleFuel.Builder saturationModifier(float f) {
            saturationModifier = f;
            return this;
        }

        public FickleFuel.Builder alwaysConsumable() {
            canAlwaysConsume = true;
            return this;
        }

        public FickleFuel build() {
            return new FickleFuel(nutrition, saturationModifier, canAlwaysConsume);
        }
    }
}
