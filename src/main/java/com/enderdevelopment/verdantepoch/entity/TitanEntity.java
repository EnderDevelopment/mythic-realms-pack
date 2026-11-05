package com.enderdevelopment.verdantepoch.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * The Verdant Titan: a god-like boss. It scales far beyond a normal
 * Iron Golem, cracks the ground with shockwaves and occasionally calls
 * down a celestial strike.
 *
 * Subclassing IronGolemEntity reuses its solid model, animations and
 * attacks while we override its traits to be truly god-like.
 */
public class TitanEntity extends IronGolemEntity {

    public TitanEntity(EntityType<? extends TitanEntity> entityType, World world) {
        super(entityType, world);
        this.setHealth(this.getMaxHealth());
    }

    public static DefaultAttributeContainer createTitanAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 420.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 22.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 14.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.34D)
                .build();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        // The titan knocks fleeing players around.
        super.onPlayerCollision(player);
        if (!this.world.isClient && this.isAlive()) {
            Vec3d push = this.getPos().subtract(player.getPos()).normalize().multiply(0.9D, 0.0D, 0.9D);
            player.addVelocity(push.x, 0.25D, push.z);
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (this.world.isClient) {
            return;
        }
        if (this.random.nextInt(60) == 0 && this.getTarget() != null) {
            // Celestial strike: a decisive lightning hit on the target.
            this.getTarget().damage(this.getDamageSources().lightningBolt(), 12.0F);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // Reinforced hide: resist three quarters of incoming damage.
        float reduced = amount * 0.25F;
        return super.damage(source, reduced);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }
}
