package com.example.kidvkid.entity;

import com.example.kidvkid.init.ModEntityTypes;
import com.example.kidvkid.init.ModParticles;
import com.example.kidvkid.item.BaconItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class BaconEntity extends AbstractArrowEntity {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final double PARTICLES = 100;
    private ItemStack arrowStack;
    private DoublePos startingPoint;
    protected BlockState inBlockState;
    protected MatrixStack hitEntities;
    protected MatrixStack piercedEntities;
    protected SoundEvent splatSoundEvent;
    public BaconEntity(EntityType<? extends com.example.kidvkid.entity.BaconEntity> type, World worldIn) {
        super(type, worldIn);
        if (this.splatSoundEvent == null) {
            this.splatSoundEvent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_hit_sound"));
        }
    }

    public BaconEntity(World worldIn) {
        super(ModEntityTypes.BACON_ENTITY.get(), worldIn);
        if (this.splatSoundEvent == null) {
            this.splatSoundEvent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_hit_sound"));
        }
    }
    public BaconEntity(EntityType<? extends AbstractArrowEntity> type, double x, double y, double z, World worldIn) {
        super(ModEntityTypes.BACON_ENTITY.get(), x, y, z, worldIn);
        if (this.splatSoundEvent == null) {
            this.splatSoundEvent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_hit_sound"));
        }
    }

    public BaconEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.BACON_ENTITY.get(), x, y, z, worldIn);
        if (this.splatSoundEvent == null) {
            this.splatSoundEvent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_hit_sound"));
        }
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
        if (x == 0 && y == 0 && z == 0) {
            return;
        }
        if (this.startingPoint == null) {
            this.startingPoint = new DoublePos(x, y, z);
        }
    }

    public BaconEntity(World worldIn, LivingEntity shooter) {
        this(ModEntityTypes.BACON_ENTITY.get(), shooter.getPosX(), shooter.getPosYEye() - (double)0.4F, shooter.getPosZ(), worldIn);
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }
        this.startingPoint = new DoublePos(shooter.getPosX(), shooter.getPosYEye() - (double)0.4F, shooter.getPosZ());
        if (this.splatSoundEvent == null) {
            this.splatSoundEvent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_hit_sound"));
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
        super.shoot(shooter, pitch, yaw, p_184547_4_, velocity, inaccuracy);
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(BaconItem::new);
    }

    private DoublePos getDoublePosition() {
        return new DoublePos(
                this.getPosX(),
                this.getPosY(),
                this.getPosZ()
        );
    }
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.inGround)  {
            //this.spawnSmokeTrail(this.getDoublePosition());
        }
        super.tick();
    }

    private void spawnSmokeTrail(DoublePos pos) {
        double d0 = (double)0.1D;
        double d1 = (double)0.1D;
        double d2 = (double)0.1D;
        try {
            try {
                DoublePos curTrailPos = this.startingPoint.clone();
                IntervalDifferences differences = new IntervalDifferences(pos, this.startingPoint);
                for(int j = 0; j < PARTICLES; ++j) {
                    curTrailPos = differences.getNextPosition(curTrailPos);
                    this.world.addParticle(ModParticles.SMOKE_TRAIL, true, curTrailPos.getX(), curTrailPos.getY(), curTrailPos.getZ(), d0, d1, d2);
                }
            } catch (CloneNotSupportedException e) {
                return;
            }
        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Called when the arrow hits a block or an entity
     */
    protected void onHit(RayTraceResult raytraceResultIn) {
        LOGGER.info("spawned trail");
        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            EntityRayTraceResult entityResult = (EntityRayTraceResult)raytraceResultIn;

            Vec3d vec3d = entityResult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
            this.setMotion(vec3d);
            Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
            this.setRawPosition(this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y, this.getPosZ() - vec3d1.z);
            this.spawnSmokeTrail(new DoublePos(this.getPosX(), this.getPosY(), this.getPosZ()));
            this.setPierceLevel((byte)127);
            this.onEntityHit((EntityRayTraceResult)raytraceResultIn);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            this.inBlockState = blockstate;
            Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
            this.setMotion(vec3d);
            Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
            this.setRawPosition(this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y, this.getPosZ() - vec3d1.z);
            this.spawnSmokeTrail(new DoublePos(this.getPosX(), this.getPosY(), this.getPosZ()));
            this.inGround = true;
            this.arrowShake = 7;
            this.setIsCritical(false);
            this.setPierceLevel((byte)0);
            this.setShotFromCrossbow(false);
            this.func_213870_w();
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
        }
        this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), this.splatSoundEvent, SoundCategory.PLAYERS, 1F, 1.0F);
    }

    private void func_213870_w() {
        if (this.hitEntities != null) {
            this.hitEntities.clear();
        }

        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }

    }

    protected static class IntervalDifferences {

        private double diffX;

        private double diffY;

        private double diffZ;

        public IntervalDifferences(DoublePos pos, DoublePos startingPoint) {
            this.diffX = (pos.getX() - startingPoint.getX()) / PARTICLES;
            this.diffY = (pos.getY() - startingPoint.getY()) / PARTICLES;
            this.diffZ = (pos.getZ() - startingPoint.getZ()) / PARTICLES;
        }

        public IntervalDifferences(double x, double y, double z) {
            this.diffX = x;
            this.diffY = y;
            this.diffZ = z;
        }

        public DoublePos getNextPosition(DoublePos curPos) {
            return new DoublePos(
                    curPos.getX() + diffX,
                    curPos.getY() + diffY,
                    curPos.getZ() + diffZ
            );
        }
    }
    protected static class DoublePos implements Cloneable {
        private double x;
        private double y;
        private double z;

        public DoublePos(double x, double y, double z) {
            this.setX(x);
            this.setY(y);
            this.setZ(z);
        }
        public DoublePos(Vec3d vec) {
            this.setX(vec.x);
            this.setY(vec.y);
            this.setZ(vec.z);
        }
        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }
        public DoublePos clone() throws CloneNotSupportedException {
            return (DoublePos) super.clone();
        }
    }
}