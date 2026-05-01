package me.therealfickle.rabid.block.entity;

import net.minecraft.world.inventory.ContainerData;

public class MRContainerData implements ContainerData {
    MatterReconstructorBlockEntity reconstructor;

    public static int DATA_COUNT = 3;

    public MRContainerData(MatterReconstructorBlockEntity blockEntity) {
        reconstructor = blockEntity;
    }

    @Override
    public int getCount() {
        return DATA_COUNT;
    }

    @Override
    public int get(int i) {
        return switch (i) {
            case 0 -> reconstructor.assemblyTime;
            case 1 -> reconstructor.fuel;
            case 2 -> reconstructor.isPowered ? 1 : 0;
            default -> 0;
        };
    }

    @Override
    public void set(int idx, int data) {
        switch (idx) {
            case 0:
                reconstructor.assemblyTime = data;
                break;
            case 1:
                reconstructor.fuel = data;
                break;
            case 2:
                reconstructor.isPowered = data == 1;
                break;
            default:
        }
    }
}
