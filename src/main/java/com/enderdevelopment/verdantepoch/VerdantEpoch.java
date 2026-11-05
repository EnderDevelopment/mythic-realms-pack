package com.enderdevelopment.verdantepoch;

import com.enderdevelopment.verdantepoch.registry.ModEntities;
import com.enderdevelopment.verdantepoch.registry.ModItems;
import com.enderdevelopment.verdantepoch.world.AmbientEnvironment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point of the Verdant Epoch mod.
 *
 * Registers all custom items and entities, and hooks the ambient
 * world behaviour that makes the environment feel alive.
 */
public class VerdantEpoch implements ModInitializer {
    public static final String MOD_ID = "verdantepoch";
    public static final Logger LOGGER = LoggerFactory.getLogger("Verdant Epoch");

    @Override
    public void onInitialize() {
        ModItems.register();
        ModEntities.register();

        // Keep the world breathing: ambient particles and regeneration.
        ServerTickEvents.END_SERVER_TICK.register(AmbientEnvironment::onServerTick);

        LOGGER.info("Verdant Epoch has awakened the world.");
    }
}
