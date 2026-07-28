package me.therealfickle.rabid.init;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.UnaryOperator;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidDataComponents {


    static void init() {
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id(id), unaryOperator.apply(DataComponentType.builder()).build());
    }

}