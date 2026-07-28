package me.therealfickle.rabid.client.init;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;

import static me.therealfickle.rabid.Rabid.id;
import static net.minecraft.client.renderer.RenderPipelines.MATRICES_FOG_LIGHT_DIR_SNIPPET;

public interface RabidPipelines {

    BlendFunction ALPHA_BLEND = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

    RenderPipeline MATTER_RECONSTRUCTOR = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.PARTICLE_SNIPPET)
                    .withLocation(id("pipeline/entity_particle_translucent"))
                    .withBlend(ALPHA_BLEND)
                    .withCull(true)
                    .build());


    RenderPipeline HOLO_ITEM = RenderPipelines.register(
            RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET)
                    .withLocation(id("pipeline/holo_item"))
                    .withVertexShader(id("core/rendertype_holo_item"))
                    .withFragmentShader(id("core/rendertype_holo_item"))
                    .withSampler("Sampler0")
                    .withSampler("Sampler2")
                    .withBlend(ALPHA_BLEND)
                    .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS)
                    .build()
    );


    static void init() {
    }
}
