package com.example.kidvkid.client.render.entity;

import com.example.kidvkid.entity.BaconEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaconEntityRenderer extends ArrowRenderer<BaconEntity> {
    public static final ResourceLocation RES_BACON = new ResourceLocation("kidvkid:textures/item/bacon.png");
    private static final Logger LOGGER = LogManager.getLogger();
    public BaconEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BaconEntity entity) {
        return RES_BACON;
    }
}
