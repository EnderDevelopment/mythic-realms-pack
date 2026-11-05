package com.enderdevelopment.verdantepoch.world;

import com.enderdevelopment.verdantepoch.entity.TitanEntity;
import com.enderdevelopment.verdantepoch.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * Makes the world feel alive on the server:
 *  - ambient fireflies drift around players at night,
 *  - planted saplings sometimes sprout on their own,
 *  - the Verdant Titan occasionally stirs in the Overworld.
 */
public final class AmbientEnvironment {
    private static int tickCounter = 0;

    private AmbientEnvironment() {
    }

    public static void onServerTick(MinecraftServer server) {
        tickCounter++;

        for (ServerWorld world : server.getWorlds()) {
            if (world.getRegistryKey() != net.minecraft.world.World.OVERWORLD) {
                continue;
            }
            updateNightParticles(world);
            if (tickCounter % 400 == 0) {
                regrowForest(world);
            }
            if (tickCounter % 1200 == 0) {
                maybeStirTitan(world);
            }
        }
    }

    private static void updateNightParticles(ServerWorld world) {
        if (world.isDay()) {
            return;
        }
        List<ServerPlayerEntity> players = world.getPlayers();
        for (ServerPlayerEntity player : players) {
            Vec3d pos = player.getPos();
            for (int i = 0; i < 3; i++) {
                double x = pos.x + (world.random.nextDouble() - 0.5D) * 6.0D;
                double y = pos.y + world.random.nextDouble() * 2.0D;
                double z = pos.z + (world.random.nextDouble() - 0.5D) * 6.0D;
                world.spawnParticles(ParticleTypes.END_ROD, x, y, z, 1,
                        0.0D, 0.2D, 0.0D, 0.0D);
            }
        }
    }

    private static void regrowForest(ServerWorld world) {
        // A handful of saplings across the loaded overworld get a nudge.
        int attempts = 0;
        for (PlayerEntity player : world.getPlayers()) {
            BlockPos base = player.getBlockPos();
            for (int i = 0; i < 8 && attempts < 20; i++, attempts++) {
                int dx = world.random.nextInt(17) - 8;
                int dz = world.random.nextInt(17) - 8;
                BlockPos target = base.add(dx, world.random.nextInt(3) - 1, dz);
                Block above = world.getBlockState(target.up()).getBlock();
                if (above == Blocks.OAK_SAPLING || above == Blocks.BIRCH_SAPLING
                        || above == Blocks.SPRUCE_SAPLING) {
                    if (above instanceof SaplingBlock) {
                        ((SaplingBlock) above).generate(world, target.up(),
                                world.getBlockState(target.up()), world.random);
                    }
                }
            }
        }
    }

    private static void maybeStirTitan(ServerWorld world) {
        // Spawn the Titan far from any player only if none is already alive.
        if (!world.getEntitiesByType(ModEntities.TITAN, e -> e.isAlive()).isEmpty()) {
            return;
        }
        List<ServerPlayerEntity> players = world.getPlayers();
        if (players.isEmpty()) {
            return;
        }
        ServerPlayerEntity player = players.get(world.random.nextInt(players.size()));
        BlockPos origin = player.getBlockPos();
        BlockPos pos = findSpawnSpot(world, origin);
        if (pos == null) {
            return;
        }
        TitanEntity titan = ModEntities.TITAN.create(world);
        if (titan != null) {
            titan.refreshPositionAndAngles(pos, world.random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(titan);
        }
    }

    private static BlockPos findSpawnSpot(ServerWorld world, BlockPos origin) {
        for (int i = 0; i < 40; i++) {
            int dx = world.random.nextInt(97) - 48;
            int dz = world.random.nextInt(97) - 48;
            BlockPos candidate = origin.add(dx, 0, dz);
            BlockPos ground = world.getTopPosition(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING, candidate);
            if (ground.getY() > origin.getY() - 20) {
                return ground.up();
            }
        }
        return null;
    }
}
