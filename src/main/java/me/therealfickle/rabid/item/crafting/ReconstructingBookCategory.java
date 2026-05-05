package me.therealfickle.rabid.item.crafting;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum ReconstructingBookCategory implements StringRepresentable {
    BUILDING("building", 0),
    MATERIALS("materials", 1),
    EQUIPMENT("equipment", 2),
    MISC("misc", 3);

    public static final Codec<ReconstructingBookCategory> CODEC = StringRepresentable.fromEnum(ReconstructingBookCategory::values);
    public static final IntFunction<ReconstructingBookCategory> BY_ID = ByIdMap.continuous(ReconstructingBookCategory::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, ReconstructingBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ReconstructingBookCategory::id);
    private final String name;
    private final int id;

    ReconstructingBookCategory(final String name, final int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    private int id() {
        return id;
    }

    public String getFolderName() {
        return "reconstructing/" + name;
    }
}
