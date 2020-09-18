package com.example.kidvkid.entity.model;

import com.example.kidvkid.entity.GiantWitherEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class GiantWitherModel<T extends GiantWitherEntity> extends SegmentedModel<T> {
    private final ModelRenderer[] upperBodyParts;
    private final ModelRenderer[] heads;
    private final ImmutableList<ModelRenderer> field_228297_f_;

    public GiantWitherModel(float p_i46302_1_) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.upperBodyParts = new ModelRenderer[3];
        this.upperBodyParts[0] = new ModelRenderer(this, 0, 16);
        this.upperBodyParts[0].addBox(-40.0F, 15.6F, -2.0F, 80.0F, 12.0F, 12.0F, p_i46302_1_);
        this.upperBodyParts[1] = (new ModelRenderer(this)).setTextureSize(this.textureWidth, this.textureHeight);
        this.upperBodyParts[1].setRotationPoint(-8.0F, 26.8F, -2.0F);
        this.upperBodyParts[1].setTextureOffset(0, 88).addBox(0.0F, 0.0F, 0.0F, 12.0F, 40.0F, 12.0F, p_i46302_1_);
        this.upperBodyParts[1].setTextureOffset(96, 88).addBox(-16.0F, 6.0F, 2.0F, 44.0F, 8.0F, 8.0F, p_i46302_1_);
        this.upperBodyParts[1].setTextureOffset(96, 88).addBox(-16.0F, 16.0F, 2.0F, 44.0F, 8.0F, 8.0F, p_i46302_1_);
        this.upperBodyParts[1].setTextureOffset(96, 88).addBox(-16.0F, 26.0F, 2.0F, 44.0F, 8.0F, 8.0F, p_i46302_1_);
        this.upperBodyParts[2] = new ModelRenderer(this, 48, 88);
        this.upperBodyParts[2].addBox(0.0F, 0.0F, 0.0F, 12.0F, 24.0F, 12.0F, p_i46302_1_);
        this.heads = new ModelRenderer[3];
        this.heads[0] = new ModelRenderer(this, 0, 0);
        this.heads[0].addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, p_i46302_1_);
        this.heads[1] = new ModelRenderer(this, 128, 0);
        this.heads[1].addBox(-16.0F, -16.0F, -16.0F, 24.0F, 24.0F, 24.0F, p_i46302_1_);
        this.heads[1].rotationPointX = -32.0F;
        this.heads[1].rotationPointY = 16.0F;
        this.heads[2] = new ModelRenderer(this, 128, 0);
        this.heads[2].addBox(-16.0F, -16.0F, -16.0F, 24.0F, 24.0F, 24.0F, p_i46302_1_);
        this.heads[2].rotationPointX = 40.0F;
        this.heads[2].rotationPointY = 16.0F;
        ImmutableList.Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.addAll(Arrays.asList(this.heads));
        builder.addAll(Arrays.asList(this.upperBodyParts));
        this.field_228297_f_ = builder.build();
    }

    public ImmutableList<ModelRenderer> getParts() {
        return this.field_228297_f_;
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = MathHelper.cos(ageInTicks * 0.1F);
        this.upperBodyParts[1].rotateAngleX = (0.065F + 0.05F * f) * (float)Math.PI;
        this.upperBodyParts[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos(this.upperBodyParts[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(this.upperBodyParts[1].rotateAngleX) * 10.0F);
        this.upperBodyParts[2].rotateAngleX = (0.265F + 0.1F * f) * (float)Math.PI;
        this.heads[0].rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.heads[0].rotateAngleX = headPitch * ((float)Math.PI / 180F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        for(int i = 1; i < 3; ++i) {
            this.heads[i].rotateAngleY = (entityIn.getHeadYRotation(i - 1) - entityIn.renderYawOffset) * ((float)Math.PI / 180F);
            this.heads[i].rotateAngleX = entityIn.getHeadXRotation(i - 1) * ((float)Math.PI / 180F);
        }

    }
}
