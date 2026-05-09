package me.therealfickle.rabid.datagen.assets;

import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import static me.therealfickle.rabid.datagen.assets.ModelHelpers.*;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        gen.createTrivialCube(RabidBlocks.SFA_BLOCK);
        createCrate(gen, RabidBlocks.SFA_CRATE);
        createLightningRod(gen, RabidBlocks.SFA_DISTRESS_LIGHT);
        gen.createHorizontallyRotatedBlock(RabidBlocks.MATTER_RECONSTRUCTOR, ModelHelpers.MATTER_RECONSTRUCTOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        gen.generateFlatItem(RabidItems.SFA_INGOT, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.POLONIUM_NUGGET, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.POLONIUM_PELLET, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.EXPERIMENTAL_HELR_CALLER, ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RabidItems.FABRICATED_INTEGRATED_COMPONENT, ModelTemplates.FLAT_ITEM);

        generate3DHeld(gen, RabidItems.POLONIUM_GLAIVE);
        gen.declareCustomModelItem(RabidItems.HERACLES);

    }

}
