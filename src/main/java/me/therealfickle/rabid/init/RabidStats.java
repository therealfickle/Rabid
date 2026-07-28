package me.therealfickle.rabid.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidStats {

    Identifier INTERACT_WITH_FICKLE_PLUSH = register("interact_with_fickle_plush", StatFormatter.DEFAULT);

    static void init() {
    }

    @SuppressWarnings("SameParameterValue")
    private static Identifier register(String string, StatFormatter statFormatter) {
        var id = id(string);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, string, id);
        Stats.CUSTOM.get(id, statFormatter);
        return id;
    }
}
