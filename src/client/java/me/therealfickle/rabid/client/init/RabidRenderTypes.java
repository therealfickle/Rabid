package me.therealfickle.rabid.client.init;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidRenderTypes {

    BlendFunction ALPHA_BLEND = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

    RenderPipeline MATTER_RECONSTRUCTOR = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.PARTICLE_SNIPPET)
                    .withLocation(id("pipeline/entity_particle_translucent"))
                    .withBlend(ALPHA_BLEND)
                    .withCull(true)
                    .build());

    Function<Identifier, RenderType> MATTER_RECONSTRUCTOR_TYPE = Util.memoize((texture) -> {
        var builder = RenderSetup.builder(MATTER_RECONSTRUCTOR)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
        return RenderType.create("rabid:entity_particle_translucent", builder);
    });

    static RenderType matterReconstructor(Identifier texture) {
        return MATTER_RECONSTRUCTOR_TYPE.apply(texture);
    }

    static void init() {
    }
}