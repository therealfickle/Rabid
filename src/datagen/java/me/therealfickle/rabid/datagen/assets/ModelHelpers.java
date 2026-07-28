package me.therealfickle.rabid.datagen.assets;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.world.item.Item;

import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;

public interface ModelHelpers {

    // region Item Model Gen Functions
    static void generate3DHeld(ItemModelGenerators gen, Item item) {
        ItemModel.Unbaked flat = ItemModelUtils.plainModel(gen.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked notFlat = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"));
        gen.itemModelOutput.accept(item, createFlatModelDispatch(flat, notFlat));
    }

    // endregion

}
