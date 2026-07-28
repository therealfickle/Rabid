package me.therealfickle.rabid.client;

import me.therealfickle.rabid.client.init.RabidRenderTypes;
import me.therealfickle.rabid.client.renderer.blockentity.FicklePlushBlockEntityRenderer;
import me.therealfickle.rabid.init.RabidBlockEntityTypes;
import me.therealfickle.rabid.init.RabidBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class RabidClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        RabidRenderTypes.init();
        BlockRenderLayerMap.putBlock(RabidBlocks.FICKLE_PLUSH, ChunkSectionLayer.CUTOUT);
        BlockEntityRenderers.register(RabidBlockEntityTypes.FICKLE_PLUSH, FicklePlushBlockEntityRenderer::new);
    }

}
