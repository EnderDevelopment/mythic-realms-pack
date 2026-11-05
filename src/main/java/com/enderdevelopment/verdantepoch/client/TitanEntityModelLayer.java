package com.enderdevelopment.verdantepoch.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * Holds the model layer identifier for the Verdant Titan so the client
 * can resolve its textured model data at startup.
 */
public final class TitanEntityModelLayer {
    public static final EntityModelLayer TITAN =
            new EntityModelLayer(new Identifier("verdantepoch", "verdant_titan"), "main");

    private TitanEntityModelLayer() {
    }
}
