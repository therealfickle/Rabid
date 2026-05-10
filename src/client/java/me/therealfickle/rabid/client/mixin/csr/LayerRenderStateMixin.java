package me.therealfickle.rabid.client.mixin.csr;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public interface LayerRenderStateMixin {

    @Invoker("submit")
    void rabid_submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, int k);

}
