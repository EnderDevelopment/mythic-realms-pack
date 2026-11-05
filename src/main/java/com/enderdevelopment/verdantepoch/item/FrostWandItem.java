package com.enderdevelopment.verdantepoch.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A wand of frost that slows and chills everything near the point the
 * wielder aims at. Frost is the patient, unyielding side of magic.
 */
public class FrostWandItem extends Item {

    public FrostWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.success(stack);
        }

        ServerWorld serverWorld = (ServerWorld) world;
        Vec3d look = user.getRotationVector();
        Vec3d impact = user.getEyePos().add(look.multiply(8.0D));

        Box area = new Box(impact.add(-3.0D, -3.0D, -3.0D), impact.add(3.0D, 3.0D, 3.0D));
        serverWorld.getEntitiesByClass(net.minecraft.entity.LivingEntity.class, area,
                        e -> e != user && e.isAlive())
                .forEach(target -> {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3));
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 1));
                });

        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_POWDER_SNOW_BREAK,
                SoundCategory.PLAYERS, 1.0F, 1.4F);
        stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        return TypedActionResult.success(stack);
    }
}
