package com.enderdevelopment.verdantepoch.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A staff that hurls explosive fireballs at whatever the wielder
 * aims at. Fire magic is the raw, destructive expression of the Epoch.
 */
public class FireStaffItem extends Item {

    public FireStaffItem(Settings settings) {
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
        Vec3d origin = user.getEyePos().add(look.multiply(0.8D));

        int shots = 5;
        for (int i = 0; i < shots; i++) {
            // Slight spread for a volley of fire.
            Vec3d dir = look
                    .add(randomJitter())
                    .normalize()
                    .multiply(1.4D);
            serverWorld.spawnEntity(new net.minecraft.entity.projectile.SmallFireballEntity(
                    serverWorld, user, dir.x, dir.y, dir.z));
        }

        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT,
                SoundCategory.PLAYERS, 1.0F, 0.8F);
        stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        return TypedActionResult.success(stack);
    }

    private Vec3d randomJitter() {
        return new Vec3d(
                (Math.random() - 0.5D) * 0.12D,
                (Math.random() - 0.5D) * 0.12D,
                (Math.random() - 0.5D) * 0.12D);
    }
}
