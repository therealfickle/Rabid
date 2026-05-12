package me.therealfickle.rabid.client.init;

import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public interface RabidRenderTypes {

    Function<Identifier, RenderType> MATTER_RECONSTRUCTOR_TYPE = Util.memoize((texture) -> {
        var builder = RenderSetup.builder(RabidPipelines.MATTER_RECONSTRUCTOR)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
        return RenderType.create("rabid:entity_particle_translucent", builder);
    });

    Function<Identifier, RenderType> HOLO_ITEM = Util.memoize(texture -> {
        RenderSetup renderSetup = RenderSetup.builder(RabidPipelines.HOLO_ITEM)
                .withTexture("Sampler0", texture)
                .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .sortOnUpload()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
        return RenderType.create("rabid:holo_item", renderSetup);
    });

    static RenderType matterReconstructor(Identifier texture) {
        return MATTER_RECONSTRUCTOR_TYPE.apply(texture);
    }

    static RenderType holoItem(Identifier texture) {
        return HOLO_ITEM.apply(texture);
    }

    static void init() {
    }
}