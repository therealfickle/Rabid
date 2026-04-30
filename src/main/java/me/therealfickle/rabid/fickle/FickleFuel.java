package me.therealfickle.rabid.fickle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.therealfickle.rabid.init.RabidAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record FickleFuel(
        boolean anyoneCanEat,
        boolean anyoneGainsFoodEffects,
        List<ConsumeEffect> negativeConsumeEffects
) implements ConsumableListener {

    public static final Codec<FickleFuel> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            Codec.BOOL.optionalFieldOf("anyone_can_eat", false).forGetter(FickleFuel::anyoneCanEat),
                            Codec.BOOL.optionalFieldOf("anyone_gains_food_effects", false).forGetter(FickleFuel::anyoneGainsFoodEffects),
                            ConsumeEffect.CODEC.listOf().optionalFieldOf("negative_consume_effects", List.of()).forGetter(FickleFuel::negativeConsumeEffects)
                    )
                    .apply(instance, FickleFuel::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FickleFuel> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FickleFuel::anyoneCanEat,
            ByteBufCodecs.BOOL,
            FickleFuel::anyoneGainsFoodEffects,
            ConsumeEffect.STREAM_CODEC.apply(ByteBufCodecs.list()),
            FickleFuel::negativeConsumeEffects,
            FickleFuel::new
    );

    @Override
    public void onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable) {
        if (livingEntity instanceof Player player && RabidAttachments.isInFickleMode(player)) {
            return;
        }

        for (ConsumeEffect effect : negativeConsumeEffects) {
            effect.apply(level, itemStack, livingEntity);
        }

    }

    @SuppressWarnings("unused")
    public static class Builder {
        boolean anyoneCanEat = false;
        boolean anyoneGainsFoodEffects = false;
        List<ConsumeEffect> negativeEffects = new ArrayList<>();

        public FickleFuel.Builder anyoneCanEat() {
            anyoneCanEat = true;
            return this;
        }

        public FickleFuel.Builder anyoneGainsFoodEffects() {
            anyoneGainsFoodEffects = true;
            return this;
        }

        public FickleFuel.Builder negativeConsumeEffect(ConsumeEffect effect) {
            negativeEffects.add(effect);
            return this;
        }

        public FickleFuel build() {
            return new FickleFuel(anyoneCanEat, anyoneGainsFoodEffects, negativeEffects);
        }
    }

}
