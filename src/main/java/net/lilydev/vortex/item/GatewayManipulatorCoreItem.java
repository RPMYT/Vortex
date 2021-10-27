package net.lilydev.vortex.item;

import net.lilydev.vortex.Vortex;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndGatewayBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GatewayManipulatorCoreItem extends Item {
    public GatewayManipulatorCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        if (world instanceof ServerWorld) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof EndGatewayBlock) {
                world.setBlockState(pos, Vortex.Blocks.GATEWAY_MANIPULATOR.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }
}