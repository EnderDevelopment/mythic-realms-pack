package com.enderdevelopment.verdantepoch.registry;

import com.enderdevelopment.verdantepoch.VerdantEpoch;
import com.enderdevelopment.verdantepoch.entity.TitanEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Registers all custom entity types with the game.
 */
public final class ModEntities {
    public static final EntityType<TitanEntity> TITAN = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(VerdantEpoch.MOD_ID, "verdant_titan"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TitanEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 2.4f))
                    .build()
    );

    private ModEntities() {
    }

    public static void register() {
        FabricDefaultAttributeRegistry.register(TITAN, TitanEntity.createTitanAttributes());
        VerdantEpoch.LOGGER.info("Registered the Verdant Titan.");
    }
}
