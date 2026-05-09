package me.therealfickle.rabid.datagen.assets;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static net.minecraft.client.data.models.BlockModelGenerators.createBooleanModelDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;
import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;
import static net.minecraft.client.data.models.model.ModelTemplates.CUBE;
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public interface ModelHelpers {

    // region Model Definitions
    TexturedModel.Provider MATTER_RECONSTRUCTOR = createDefault(ModelHelpers::matterReconstructor, CUBE);

    static TextureMapping matterReconstructor(Block block) {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(block, "/base"))
                .put(TextureSlot.NORTH, getBlockTexture(block, "/front"))
                .put(TextureSlot.SOUTH, getBlockTexture(block, "/back"))
                .put(TextureSlot.UP, getBlockTexture(block, "/top"))
                .put(TextureSlot.EAST, getBlockTexture(block, "/base"))
                .put(TextureSlot.WEST, getBlockTexture(block, "/base"))
                .put(TextureSlot.DOWN, getBlockTexture(block, "/base"));
    }
    // endregion


    // region Block Model Gen Functions
    static void createLightningRod(BlockModelGenerators gen, Block block) {
        var model = ModelTemplates.LIGHTNING_ROD.create(block, TextureMapping.defaultTexture(block), gen.modelOutput);
        MultiVariant variant = plainVariant(model);
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(createBooleanModelDispatch(BlockStateProperties.POWERED, variant, variant))
                        .with(ROTATIONS_COLUMN_WITH_FACING)
        );

    }

    static void createCrate(BlockModelGenerators gen, Block block) {
        var model = plainVariant(TexturedModel.COLUMN_ALT.create(block, gen.modelOutput));
        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(ROTATIONS_COLUMN_WITH_FACING));
    }

    PropertyDispatch<VariantMutator> ROTATIONS_COLUMN_WITH_FACING = PropertyDispatch.modify(BlockStateProperties.FACING)
            .select(Direction.DOWN, BlockModelGenerators.X_ROT_180)
            .select(Direction.UP, BlockModelGenerators.NOP)
            .select(Direction.NORTH, BlockModelGenerators.X_ROT_90)
            .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
            .select(Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
            .select(Direction.EAST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90));
    // endregion

    // region Item Model Gen Functions
    static void generate3DHeld(ItemModelGenerators gen, Item item) {
        ItemModel.Unbaked flat = ItemModelUtils.plainModel(gen.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked notFlat = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"));
        gen.itemModelOutput.accept(item, createFlatModelDispatch(flat, notFlat));
    }

    // endregion

}
