package com.enderdevelopment.verdantepoch.client.model;

import com.enderdevelopment.verdantepoch.entity.TitanEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/**
 * A chunky, god-like model for the Verdant Titan. Built from simple boxes
 * (a broad head, a massive torso and heavy limbs) so it reads as a towering
 * colossus rather than an ordinary golem.
 */
public class TitanEntityModel extends AnimalModel<TitanEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public TitanEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelPartData root = ModelPartData.create();
        ModelPartData head = root.addChild("head", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -10.0F, -5.0F, 9.0F, 9.0F, 9.0F),
                ModelTransform.pivot(0.0F, -7.0F, -2.0F));
        head.addChild("forehead", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3.0F, -3.0F, -2.0F, 6.0F, 4.0F, 1.0F),
                ModelTransform.pivot(0.0F, -6.0F, -6.0F));

        root.addChild("body", ModelPartBuilder.create()
                        .uv(0, 40).cuboid(-7.0F, 0.0F, -4.0F, 14.0F, 18.0F, 8.0F),
                ModelTransform.pivot(0.0F, -19.0F, 0.0F));

        root.addChild("right_arm", ModelPartBuilder.create()
                        .uv(40, 22).cuboid(-4.0F, -2.0F, -3.0F, 6.0F, 20.0F, 6.0F),
                ModelTransform.pivot(-7.0F, -17.0F, 0.0F));
        root.addChild("left_arm", ModelPartBuilder.create()
                        .uv(40, 22).cuboid(-2.0F, -2.0F, -3.0F, 6.0F, 20.0F, 6.0F),
                ModelTransform.pivot(7.0F, -17.0F, 0.0F));

        root.addChild("right_leg", ModelPartBuilder.create()
                        .uv(0, 22).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 6.0F),
                ModelTransform.pivot(-4.0F, 0.0F, 0.0F));
        root.addChild("left_leg", ModelPartBuilder.create()
                        .uv(0, 22).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 6.0F),
                ModelTransform.pivot(4.0F, 0.0F, 0.0F));

        return TexturedModelData.of(root, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return java.util.List.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return java.util.List.of(this.body, this.rightArm, this.leftArm,
                this.rightLeg, this.leftLeg);
    }

    @Override
    public void setAngles(TitanEntity titan, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;

        this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
        this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    public void setAngles(TitanEntity entity, float limbAngle, float limbDistance,
                          float animationProgress) {
        // Unused variant; the full signature is used by the renderer.
    }

    @Override
    public void render(MatrixStack matrices, net.minecraft.client.render.VertexConsumer vertexConsumer,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
