package net.lilydev.vortex.world;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class VolatileFluidExplosionDamageSource extends DamageSource {
    private int distance = 0;

    public VolatileFluidExplosionDamageSource() {
        super("volatileFluidExplosion");
        this.setExplosive();
    }

    public void propagate() {
        this.distance++;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        return new TranslatableText("death.attack.volatile_fluid");
    }
}