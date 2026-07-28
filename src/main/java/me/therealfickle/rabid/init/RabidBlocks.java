package me.therealfickle.rabid.init;

import me.therealfickle.rabid.block.FicklePlushBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidBlocks {
    Block FICKLE_PLUSH = register("fickle_plush", FicklePlushBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(0.2F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
    );

    static void init() {
    }

    static Block registerNoItem(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        var key = ResourceKey.create(Registries.BLOCK, id(name));
        var block = function.apply(properties.setId(key));

        return Registry.register(BuiltInRegistries.BLOCK, id(name), block);
    }

    static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        var regBlock = registerNoItem(name, function, properties);
        RabidItems.register(name, (params) -> new BlockItem(regBlock, params), new Item.Properties());

        return regBlock;
    }
}
