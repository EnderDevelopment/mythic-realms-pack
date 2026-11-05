package com.enderdevelopment.verdantepoch.client.render;

import com.enderdevelopment.verdantepoch.client.model.TitanEntityModel;
import com.enderdevelopment.verdantepoch.entity.TitanEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

/**
 * Renders the Verdant Titan with its custom colossus model, using the
 * vanilla iron-golem texture so the boss reads clearly as a hulking giant
 * without needing a bundled texture file.
 */
public class TitanEntityRenderer extends MobEntityRenderer<TitanEntity, TitanEntityModel> {

    private static final Identifier TEXTURE =
            new Identifier("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public TitanEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TitanEntityModel(context.getPart(TitanEntityModelLayer.TITAN)), 0.9F);
    }

    @Override
    public Identifier getTexture(TitanEntity entity) {
        return TEXTURE;
    }
}
