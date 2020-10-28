package com.example.kidvkid.init;

import com.example.kidvkid.KidVKid;
import com.example.kidvkid.entity.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link EntityType}s.
 * Suppliers that create EntityTypes are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the EntityType Registry Event is fired by Forge and it is time for the mod to
 * register its EntityTypes, our EntityTypes are created and registered by the DeferredRegister.
 * The EntityType Registry Event will always be called after the Block and Item registries are filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, KidVKid.MODID);

    public static final String GIANT_WITHER_NAME = "giant_wither";
    public static final String BACON_NAME = "bacon";

    public static final RegistryObject<EntityType<GiantWitherEntity>> GIANT_WITHER = ENTITY_TYPES.register(GIANT_WITHER_NAME, () ->
            EntityType.Builder.<GiantWitherEntity>create(GiantWitherEntity::new, EntityClassification.CREATURE)
                    .size(3.6F, 14.0F)
                    .build(new ResourceLocation(KidVKid.MODID, GIANT_WITHER_NAME).toString())
    );
    public static final RegistryObject<EntityType<BaconEntity>> BACON_ENTITY = ENTITY_TYPES.register(BACON_NAME, () ->
            EntityType.Builder.<BaconEntity>create(BaconEntity::new, EntityClassification.MISC)
                    //.setCustomClientFactory((spawnEntity, world) -> new BaconEntity(world))
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(24)
                    .setUpdateInterval(60)
                    .size(3.5F, 3.5F)
                    .build(new ResourceLocation(KidVKid.MODID, BACON_NAME).toString())
    );
}