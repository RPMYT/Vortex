package net.lilydev.vortex.world;

import net.minecraft.entity.damage.DamageSource;

public class AntirealityDamageSource extends DamageSource {
    protected AntirealityDamageSource() {
        super("antireality");
        this.setBypassesArmor();
        this.setUnblockable();
    }
}