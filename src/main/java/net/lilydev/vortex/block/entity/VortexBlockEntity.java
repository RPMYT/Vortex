package net.lilydev.vortex.block.entity;

import net.lilydev.vortex.Vortex;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class VortexBlockEntity extends BlockEntity {
    public VortexBlockEntity(BlockPos pos, BlockState state) {
        super(Vortex.BlockEntities.VORTEX, pos, state);
    }
}