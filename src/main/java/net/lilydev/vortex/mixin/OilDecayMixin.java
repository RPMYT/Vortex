package net.lilydev.vortex.mixin;

import net.lilydev.vortex.fluid.OilFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FluidBlock.class)
public class OilDecayMixin {
    @Inject(method = "randomTick", at = @At("HEAD"))
    private void decay(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (((FluidBlock) (Object) this).fluid instanceof OilFluid) {
            BlockPos down = pos.down();
            BlockState downState = world.getBlockState(down);
            if (downState.isIn(BlockTags.DIRT) && downState.getBlock() != Blocks.COARSE_DIRT) {
                world.setBlockState(down, Blocks.COARSE_DIRT.getDefaultState());
                BlockPos[] sides = new BlockPos[4];

                sides[0] = down.north();
                sides[1] = down.east();
                sides[2] = down.south();
                sides[3] = down.west();

                for (BlockPos side : sides) {
                    if (world.getBlockState(pos).isIn(BlockTags.DIRT)) {
                        world.setBlockState(side, Blocks.COARSE_DIRT.getDefaultState());
                    }
                }
            }
        }
    }
}
