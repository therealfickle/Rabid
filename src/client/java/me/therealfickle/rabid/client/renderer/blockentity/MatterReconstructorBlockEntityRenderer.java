package me.therealfickle.rabid.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.therealfickle.rabid.block.MatterReconstructorBlock;
import me.therealfickle.rabid.block.entity.MatterReconstructorBlockEntity;
import me.therealfickle.rabid.client.init.RabidRenderTypes;
import me.therealfickle.rabid.client.renderer.blockentity.state.MatterReconstructorRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

import static me.therealfickle.rabid.Rabid.id;

public class MatterReconstructorBlockEntityRenderer implements BlockEntityRenderer<MatterReconstructorBlockEntity, MatterReconstructorRenderState> {

    public MatterReconstructorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public MatterReconstructorRenderState createRenderState() {
        return new MatterReconstructorRenderState();
    }

    @Override
    public void extractRenderState(MatterReconstructorBlockEntity reconstructor, MatterReconstructorRenderState state, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(reconstructor, state, f, vec3, crumblingOverlay);
        state.facing = reconstructor.getBlockState().getValue(MatterReconstructorBlock.FACING);
        state.powered = reconstructor.isPowered;
        var level = reconstructor.getLevel();
        if (level == null) return;
        var speed = 5.0f;
        state.tickingTime = (level.getGameTime() + f) * speed;

        var rand = level.random.forkPositional().at(reconstructor.getBlockPos());
        rand.setSeed(reconstructor.getBlockPos().asLong());
        var item = BuiltInRegistries.ITEM.getRandom(rand)
                .orElse(Items.ACACIA_BOAT.builtInRegistryHolder())
                .value().getDefaultInstance();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(
                state.resultItem.delegate, item, ItemDisplayContext.GROUND, Minecraft.getInstance().level, null, 0
        );
        var color = 0xff_fff081;
        state.resultItem.renderTypeGetter = (quadAtlas, layer) -> RabidRenderTypes.holoItem(quadAtlas.getTextureId());
    }

    @Override
    public void submit(MatterReconstructorRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();
        var dir = state.facing;

        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.tickingTime - dir.getOpposite().toYRot()));

        poseStack.translate(0, 1.5, 0);

        var light = 15728880;

        //0x0f_fff081
        state.resultItem.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, 0);

//        collector.submitCustomGeometry(poseStack, RabidRenderTypes.matterReconstructor(AREA), (pose, vertexConsumer) -> renderShape(state, pose, vertexConsumer, light));

//        collector.submitBlock(poseStack, Blocks.LOOM.defaultBlockState(), light, OverlayTexture.NO_OVERLAY, 0);

       /* for (var x = -2; x <= 2; x++) {
            for (var y = 0; y <= 2; y++) {
                for (var z = -2; z <= 2; z++) {
                    poseStack.pushPose();
                    poseStack.translate(x, y, z);
                    collector.submitBlock(poseStack, Blocks.LOOM.defaultBlockState(), light, 0, 0);
                    poseStack.popPose();
                }
            }
        }

*/
        poseStack.popPose();
    }

    private void renderCube(EnumSet<Direction> enumSet, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float offestDown = 1f;
        float offestUp = 1f;
        this.renderFace(matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderFace(matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderFace(matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderFace(matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderFace(matrix4f, vertexConsumer, 0.0F, 1.0F, offestDown, offestDown, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderFace(matrix4f, vertexConsumer, 0.0F, 1.0F, offestUp, offestUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }

    void renderFace(
            Matrix4f matrix4f,
            VertexConsumer vertexConsumer,
            float f,
            float g,
            float h,
            float i,
            float j,
            float k,
            float l,
            float m,
            Direction direction
    ) {
        vertexConsumer.addVertex(matrix4f, f, h, j);
        vertexConsumer.addVertex(matrix4f, g, h, k);
        vertexConsumer.addVertex(matrix4f, g, i, l);
        vertexConsumer.addVertex(matrix4f, f, i, m);
    }

    private static void renderShape(MatterReconstructorRenderState state, PoseStack.Pose pose, VertexConsumer vertexConsumer, int light) {
//        if (state.powered) {
        vertex(vertexConsumer, pose, light, 0, 0, 0, 0, 0);
        vertex(vertexConsumer, pose, light, 0, 0, 3, 0, 3f);
        vertex(vertexConsumer, pose, light, 0, 3, 3, 3f, 3f);
        vertex(vertexConsumer, pose, light, 0, 3, 0, 3f, 0);
//        }
    }

    static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, int light, float x, float y, float z, float u, float v) {
        vertexConsumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0, 1, 0);
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    public static Identifier AREA = id("textures/block/matter_reconstructor/area.png");
    private static final RenderType RENDER_TYPE = RenderTypes.beaconBeam(AREA, true);

}
