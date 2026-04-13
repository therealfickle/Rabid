package me.therealfickle.rabid.init;

import me.therealfickle.rabid.block.FicklePlushBlock;
import me.therealfickle.rabid.block.FicliumCrateBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidBlocks {
    Block FICLIUM_BLOCK = register("ficlium_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK));
    Block FICLIUM_CRATE = register("ficlium_crate", FicliumCrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK));
    Block POD_DISTRESS_LIGHT = register("pod_distress_light", LightningRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK));
    Block FICKLE_PLUSH = register("fickle_plush", FicklePlushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL));

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
