package com.example.kidvkid.client.render.entity;

import com.example.kidvkid.entity.GiantWitherEntity;
import com.example.kidvkid.entity.model.GiantWitherModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GiantWitherRenderer extends MobRenderer<GiantWitherEntity, GiantWitherModel<GiantWitherEntity>> {
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("kidvkid:textures/entity/giant_wither.png");

    public GiantWitherRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GiantWitherModel<>(0.0F), 1.0F);
    }

    protected int getBlockLight(GiantWitherEntity entityIn, float partialTicks) {
        return 15;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(GiantWitherEntity entity) {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
    }

    protected void preRenderCallback(GiantWitherEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 2.0F;
        int i = entitylivingbaseIn.getInvulTime();
        if (i > 0) {
            f -= ((float)i - partialTickTime) / 220.0F * 0.5F;
        }

        matrixStackIn.scale(f, f, f);
    }
}
