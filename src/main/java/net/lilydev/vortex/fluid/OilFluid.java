package net.lilydev.vortex.fluid;

import net.lilydev.vortex.Vortex;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class OilFluid extends VortexBasicFluid {
    @Override
    public Fluid getStill() {
        return Vortex.Fluids.OIL_STILL;
    }

    @Override
    public Fluid getFlowing() {
        return Vortex.Fluids.OIL_FLOWING;
    }

    @Override
    public Item getBucketItem() {
        return Vortex.Items.BUCKET_OIL;
    }

    @Override
    protected int getFlowSpeed(WorldView view) {
        return 1;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView view) {
        return 2;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return Vortex.Blocks.OIL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public int getTickRate(WorldView view) {
        return 35;
    }

    public static class Flowing extends OilFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }

    public static class Still extends OilFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    @Override
    public boolean isVolatile() {
        return true;
    }
}
