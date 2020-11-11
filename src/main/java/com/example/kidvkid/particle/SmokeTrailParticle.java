package com.example.kidvkid.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokeTrailParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite field_217585_C;

    protected SmokeTrailParticle(World p_i51010_1_, double p_i51010_2_, double p_i51010_4_, double p_i51010_6_, double p_i51010_8_, double p_i51010_10_, double p_i51010_12_, float p_i51010_14_, IAnimatedSprite p_i51010_15_) {
        super(p_i51010_1_, p_i51010_2_, p_i51010_4_, p_i51010_6_, 0.0D, 0.0D, 0.0D);
        this.field_217585_C = p_i51010_15_;
        this.motionX *= (double)0F;
        this.motionY *= (double)0F;
        this.motionZ *= (double)0F;
        this.motionX += p_i51010_8_;
        this.motionY += p_i51010_10_;
        this.motionZ += p_i51010_12_;
        float f = (float)(0.3F);
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale *= 0.75F * p_i51010_14_;
        this.maxAge = (int)(80.0D);
        this.selectSpriteWithAge(p_i51010_15_);
    }
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getScale(float scaleFactor) {
        return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.field_217585_C);
            this.motionY += 0D;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.posY == this.prevPosY) {
                this.motionX *= 0D;
                this.motionZ *= 0D;
            }

            this.motionX *= (double) 0F;
            this.motionY *= (double) 0F;
            this.motionZ *= (double) 0F;
            if (this.onGround) {
                this.motionX *= (double) 0F;
                this.motionZ *= (double) 0F;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i51045_1_) {
            this.spriteSet = p_i51045_1_;
        }

        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmokeTrailParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 5.0F, this.spriteSet);
        }
    }
}
