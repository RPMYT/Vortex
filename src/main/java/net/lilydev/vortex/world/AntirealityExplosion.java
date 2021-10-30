package net.lilydev.vortex.world;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AntirealityExplosion extends Explosion {
    public AntirealityExplosion(World world, double x, double y, double z) {
        super(world, null, x, y, z, 42.0F);
        this.behavior = new AntirealityExplosion.Behaviour();
    }

    @Override
    public DamageSource getDamageSource() {
        if (this.damageSource instanceof AntirealityDamageSource) {
            return this.damageSource;
        } else {
            return new AntirealityDamageSource();
        }
    }

    @Override
    public void affectWorld(boolean particles) {
        this.getAffectedBlocks().forEach(pos -> this.world.removeBlock(pos, false));
    }

    public static class Behaviour extends ExplosionBehavior {
        @Override
        public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
            return Optional.of(0.0F);
        }
    }
}