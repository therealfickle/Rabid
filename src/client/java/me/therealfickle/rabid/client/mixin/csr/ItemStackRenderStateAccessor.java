package me.therealfickle.rabid.client.mixin.csr;


import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackRenderState.class)
public interface ItemStackRenderStateAccessor {

    @Accessor("layers")
    ItemStackRenderState.LayerRenderState[] rabid_layers();

    @Accessor("activeLayerCount")
    int rabid_activeLayerCount();

}