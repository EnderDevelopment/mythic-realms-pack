package com.enderdevelopment.verdantepoch.client;

import com.enderdevelopment.verdantepoch.client.model.TitanEntityModel;
import com.enderdevelopment.verdantepoch.client.render.TitanEntityRenderer;
import com.enderdevelopment.verdantepoch.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

/**
 * Client-side setup: registers the Verdant Titan model layer and renderer.
 */
public class VerdantEpochClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(
                TitanEntityModelLayer.TITAN, TitanEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TITAN, TitanEntityRenderer::new);
    }
}
