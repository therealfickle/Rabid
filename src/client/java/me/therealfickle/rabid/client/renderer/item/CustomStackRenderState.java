package me.therealfickle.rabid.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import me.therealfickle.rabid.client.mixin.csr.ItemStackRenderStateAccessor;
import me.therealfickle.rabid.client.mixin.csr.LayerRenderStateMixin;
import net.fabricmc.fabric.api.renderer.v1.render.ItemRenderTypeGetter;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class CustomStackRenderState {
    public ItemStackRenderState delegate = new ItemStackRenderState();
    public ItemRenderTypeGetter renderTypeGetter = null;

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor) {
        for (int idx = 0; idx < getActiveLayerCount(); idx++) {
            var layer = getLayer(idx);
            if (renderTypeGetter != null) {
                layer.setRenderTypeGetter(renderTypeGetter);
                layer.emitter().emit();
            }
            ((LayerRenderStateMixin) layer).rabid_submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
        }
    }


    public int getActiveLayerCount() {
        return ((ItemStackRenderStateAccessor) delegate).rabid_activeLayerCount();
    }

    public ItemStackRenderState.LayerRenderState getLayer(int idx) {
        return ((ItemStackRenderStateAccessor) delegate).rabid_layers()[idx];
    }

}