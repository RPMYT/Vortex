package net.lilydev.vortex.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayFuseMixin {
    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void tickFuse(World world, BlockPos pos, BlockState state, EndGatewayBlockEntity blockEntity, CallbackInfo ci) {

    }
}
