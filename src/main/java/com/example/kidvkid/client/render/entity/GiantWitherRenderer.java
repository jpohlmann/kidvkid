package com.example.kidvkid.client.render.entity;

import com.example.kidvkid.entity.GiantWitherEntity;
import com.example.kidvkid.entity.model.GiantWitherModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Renderer for a Giant Wither.  Needed to extend from MobRenderer rather that WitherRenderer because the texture
 * locations are final for the WitherRenderer, so I couldn't change them.
 */
public class GiantWitherRenderer extends MobRenderer<GiantWitherEntity, GiantWitherModel<GiantWitherEntity>> {
    /**
     * Texture for when the wither is invulnerable.  We really aren't using this, because the giant wither isn't ever
     * invulnerable.  Could pull this out in a future iteration.
     */
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");

    /**
     * Custom texture for the giant wither.
     */
    private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("kidvkid:textures/entity/giant_wither.png");

    /**
     * Default constructor
     * @param renderManagerIn Render manager passed in by default
     */
    public GiantWitherRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GiantWitherModel<>(0.0F), 1.0F);
    }

    /**
     * Copy/pasted in function from WitherRenderer
     *
     * @param entityIn
     * @param partialTicks
     * @return
     */
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

    /**
     * Copy/pasted in function from WitherRenderer
     *
     * @param entitylivingbaseIn
     * @param matrixStackIn
     * @param partialTickTime
     */
    protected void preRenderCallback(GiantWitherEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 2.0F;
        int i = entitylivingbaseIn.getInvulTime();
        if (i > 0) {
            f -= ((float)i - partialTickTime) / 220.0F * 0.5F;
        }

        matrixStackIn.scale(f, f, f);
    }
}
