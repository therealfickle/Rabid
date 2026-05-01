package me.therealfickle.rabid.block.entity;

import net.minecraft.world.inventory.ContainerData;

class MRContainerData implements ContainerData {
    MatterReconstructorBlockEntity reconstructor;

    public MRContainerData(MatterReconstructorBlockEntity blockEntity) {
        reconstructor = blockEntity;
    }

    @Override
    public int get(int i) {
        return switch (i) {
            case 0 -> reconstructor.assemblyTime;
            case 1 -> reconstructor.fuel;
            default -> 0;
        };
    }

    @Override
    public void set(int i, int j) {
        switch (i) {
            case 0:
                reconstructor.assemblyTime = j;
                break;
            case 1:
                reconstructor.fuel = j;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
