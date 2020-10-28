package com.example.kidvkid.entity;

import com.example.kidvkid.init.ModEntityTypes;
import com.example.kidvkid.item.BaconItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaconEntity extends AbstractArrowEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private ItemStack arrowStack;

    public BaconEntity(EntityType<? extends com.example.kidvkid.entity.BaconEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BaconEntity(World worldIn) {
        super(ModEntityTypes.BACON_ENTITY.get(), worldIn);
    }
    public BaconEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.BACON_ENTITY.get(), x, y, z, worldIn);
    }

    public BaconEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityTypes.BACON_ENTITY.get(), shooter, worldIn);
        LOGGER.info("Entity shot");
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        LOGGER.info("Created the spawn packet");
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(BaconItem::new);
    }
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.inGround) {
            if (this.timeInGround % 5 == 0) {
                this.spawnPotionParticles(1);
            }
        } else {
            this.spawnPotionParticles(2);
        }
    }

    private void spawnPotionParticles(int particleCount) {
        int i = 3;
        if (i != -1 && particleCount > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < particleCount; ++j) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), d0, d1, d2);
            }

        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        super.onHit(raytraceResultIn);
        LOGGER.info("OMG HIT SOMETHING");
    }
}