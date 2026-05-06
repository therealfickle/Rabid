package me.therealfickle.rabid.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import me.therealfickle.rabid.block.FicklePlushBlock;
import me.therealfickle.rabid.block.entity.FicklePlushBlockEntity;
import me.therealfickle.rabid.client.renderer.blockentity.state.FicklePlushRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class FicklePlushBlockEntityRenderer implements BlockEntityRenderer<FicklePlushBlockEntity, FicklePlushRenderState> {

    @SuppressWarnings("deprecation")
    public static final RenderType RENDER_TYPE = RenderTypes.armorCutoutNoCull(TextureAtlas.LOCATION_BLOCKS);
    private final BlockRenderDispatcher blockDispatcher;

    public FicklePlushBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockDispatcher = context.blockRenderDispatcher();
    }

    @Override
    public FicklePlushRenderState createRenderState() {
        return new FicklePlushRenderState();
    }

    float durationInTicks = 5;

    @Override
    public void extractRenderState(FicklePlushBlockEntity plush, FicklePlushRenderState state, float tickDelta, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(plush, state, tickDelta, vec3, crumblingOverlay);
        state.facing = plush.getBlockState().getValue(FicklePlushBlock.FACING);
        if (plush.squishStartedAtTick != 0 && plush.getLevel() != null) {
            state.animationProgress = ((plush.getLevel().getGameTime() - plush.squishStartedAtTick) + tickDelta) / durationInTicks;
        } else {
            state.animationProgress = 0.0F;
        }
    }

    @Override
    public void submit(FicklePlushRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();

        if (state.animationProgress >= 0f && state.animationProgress <= 1f) {
            float f = 0.015625F;
            float g = (float) (state.animationProgress * Math.PI);
            float h = -10 * (Mth.cos(g) + 0.5f) * Mth.sin(g);
            float scale = h * f;
            float sideScale = scale * -2f;
            poseStack.translate(sideScale / -2f, 0, sideScale / -2f);
            poseStack.scale(1f + sideScale, 1f + scale, 1f + sideScale);
        }

        var model = blockDispatcher.getBlockModel(state.blockState);
        collector.submitBlockModel(
                poseStack, RENDER_TYPE, model,
                0.0F, 0.0F, 0.0F,
                state.lightCoords, OverlayTexture.NO_OVERLAY, 0
        );

        var breakProgress = state.breakProgress;
        if (breakProgress != null) {
            collector.submitCustomGeometry(poseStack, ModelBakery.DESTROY_TYPES.get(breakProgress.progress()), (pose, vertexConsumer) -> {
                var decal = new SheetedDecalTextureGenerator(vertexConsumer, breakProgress.cameraPose(), 1f);
                ModelBlockRenderer.renderModel(
                        pose, decal, model,
                        0.0F, 0.0F, 0.0F,
                        state.lightCoords, OverlayTexture.NO_OVERLAY
                );
            });
        }

        poseStack.popPose();
    }

}