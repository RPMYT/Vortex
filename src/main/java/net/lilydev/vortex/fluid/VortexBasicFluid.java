package net.lilydev.vortex.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

public abstract class VortexBasicFluid extends FlowableFluid {

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess access, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? access.getBlockEntity(pos) : null;
        Block.dropStacks(state, access, pos, blockEntity);

        if (this.isVolatile() && access instanceof ServerWorld world && state.getBlock() instanceof FireBlock) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true, Explosion.DestructionType.DESTROY);
        }
    }

    @Override
    protected float getBlastResistance() {
        return this.isVolatile() ? 0.0F : 100.0F;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView view, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView view) {
        return 5;
    }



    public boolean isVolatile() {
        return false;
    }
}
