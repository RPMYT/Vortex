package net.lilydev.vortex.block.machine;

import net.lilydev.vortex.Vortex;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndGatewayBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class VortexAcceleratorBlock extends Block {
    public static final Direction[] SIDES;

    public static final BooleanProperty POWERED;

    public VortexAcceleratorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(POWERED, context.getWorld().isReceivingRedstonePower(context.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            boolean bl = state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.getBlockTickScheduler().schedule(pos, this, 4);
                } else {
                    world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                }
            }

            for (Direction side : SIDES) {
                BlockPos there = pos.offset(side);
                Block that = world.getBlockState(there).getBlock();

                if (that instanceof EndGatewayBlock) {
                    int accelerators = 0;

                    for (Direction direction : SIDES) {
                        BlockPos position = there.offset(direction);
                        if (world.getBlockState(position).getBlock() instanceof VortexAcceleratorBlock) {
                            if (world.getBlockState(position).get(POWERED)) {
                                accelerators++;
                            }
                        }
                    }

                    if (accelerators >= 4) {
                        Vortex.LOGGER.info("Collapsing end gateway at " + there + "!");
                    }
                }
            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
        }
    }

    static {
        POWERED = Properties.POWERED;
        SIDES = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    }
}
