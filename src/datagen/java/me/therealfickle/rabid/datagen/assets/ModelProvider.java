package me.therealfickle.rabid.datagen.assets;

import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static net.minecraft.client.data.models.BlockModelGenerators.createBooleanModelDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        gen.createTrivialCube(RabidBlocks.FICLIUM_BLOCK);
        createCrate(gen, RabidBlocks.FICLIUM_CRATE);
        createLightningRod(gen, RabidBlocks.POD_DISTRESS_LIGHT);


    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        gen.generateFlatItem(RabidItems.FICLIUM_INGOT, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.POLONIUM_NUGGET, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.POLONIUM_PELLET, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.EXPERIMENTAL_HELR_CALLER, ModelTemplates.FLAT_ITEM);

    }

    public final void createLightningRod(BlockModelGenerators gen, Block block) {
        var model = ModelTemplates.LIGHTNING_ROD.create(block, TextureMapping.defaultTexture(block), gen.modelOutput);
        MultiVariant variant = plainVariant(model);
        gen.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(createBooleanModelDispatch(BlockStateProperties.POWERED, variant, variant))
                                .with(ROTATIONS_COLUMN_WITH_FACING)
                );

    }

    public void createCrate(BlockModelGenerators gen, Block block) {
        var model = plainVariant(TexturedModel.COLUMN_ALT.create(block, gen.modelOutput));
        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(ROTATIONS_COLUMN_WITH_FACING));
    }

    private static final PropertyDispatch<VariantMutator> ROTATIONS_COLUMN_WITH_FACING = PropertyDispatch.modify(BlockStateProperties.FACING)
            .select(Direction.DOWN, BlockModelGenerators.X_ROT_180)
            .select(Direction.UP, BlockModelGenerators.NOP)
            .select(Direction.NORTH, BlockModelGenerators.X_ROT_90)
            .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
            .select(Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
            .select(Direction.EAST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90));
}
