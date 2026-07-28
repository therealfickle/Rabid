package me.therealfickle.rabid.datagen.assets;

import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import static me.therealfickle.rabid.datagen.assets.ModelHelpers.generate3DHeld;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        gen.generateFlatItem(RabidItems.EXPERIMENTAL_HELR_CALLER, ModelTemplates.FLAT_ITEM);
        generate3DHeld(gen, RabidItems.GOTME);
        generate3DHeld(gen, RabidItems.QUANTUM_ENERGY_CANNON);
    }

}
