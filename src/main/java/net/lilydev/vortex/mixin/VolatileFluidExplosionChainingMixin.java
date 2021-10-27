package net.lilydev.vortex.mixin;

import net.lilydev.vortex.fluid.VortexBasicFluid;
import net.lilydev.vortex.world.VolatileFluidExplosionDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class VolatileFluidExplosionChainingMixin {
    @Inject(method = "onDestroyedByExplosion", at = @At("HEAD"))
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            if (((Block) (Object) this) instanceof FluidBlock block) {
                if (block.fluid instanceof VortexBasicFluid fluid && fluid.isVolatile()) {
                    VolatileFluidExplosionDamageSource source;
                    if (explosion.getDamageSource() instanceof VolatileFluidExplosionDamageSource damageSource) {
                        source = damageSource;
                        source.propagate();
                    } else {
                        source = new VolatileFluidExplosionDamageSource();
                    }

                    if (source.getDistance() <= 150) {
                        world.createExplosion(explosion.getCausingEntity(), source, null, pos.getX(), pos.getY(), pos.getZ(), 6.0F, true, Explosion.DestructionType.BREAK);
                    }
                }
            }
        }
    }
}