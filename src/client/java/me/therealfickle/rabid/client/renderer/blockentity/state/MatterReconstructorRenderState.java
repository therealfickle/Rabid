package me.therealfickle.rabid.client.renderer.blockentity.state;

import me.therealfickle.rabid.client.renderer.item.CustomStackRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class MatterReconstructorRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public boolean powered = false;
    public float tickingTime = 0;
    public CustomStackRenderState resultItem = new CustomStackRenderState();
}
