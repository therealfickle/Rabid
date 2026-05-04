package me.therealfickle.rabid.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class MatterReconstructorRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public boolean powered = false;
}
