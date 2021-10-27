package net.lilydev.vortex.mixin;

import net.lilydev.vortex.fluid.VortexBasicFluid;
import net.lilydev.vortex.world.VolatileFluidExplosionDamageSource;
import net.minecraft.block.FireBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public class VolatileFluidIgnitionMixin {
    @Inject(method = "trySpreadingFire", at = @At("HEAD"))
    private void ignite(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge, CallbackInfo ci) {
        FluidState state = world.getFluidState(pos);
        if (state.getFluid() instanceof VortexBasicFluid fluid && fluid.isVolatile()) {
            if (world instanceof ServerWorld) {
                world.createExplosion(null, new VolatileFluidExplosionDamageSource(), null, pos.getX(), pos.getY(), pos.getZ(), 6.0F, true, Explosion.DestructionType.BREAK);
            }
        }
    }
}