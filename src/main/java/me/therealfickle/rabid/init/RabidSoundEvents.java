package me.therealfickle.rabid.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidSoundEvents {

    Holder<SoundEvent> FICKLE_PLUSH_SQUISH = registerForHolder("block.fickle_plush.squish");

    static void init() {
    }

    @SuppressWarnings("SameParameterValue")
    private static Holder.Reference<SoundEvent> registerForHolder(String name) {
        return registerForHolder(id(name));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(Identifier id) {
        return registerForHolder(id, id);
    }

    private static Holder.Reference<SoundEvent> registerForHolder(Identifier id, Identifier identifier2) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(identifier2));
    }


}
