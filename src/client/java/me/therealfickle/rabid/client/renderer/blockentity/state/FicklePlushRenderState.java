package me.therealfickle.rabid.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class FicklePlushRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public float animationProgress = 0;
}
