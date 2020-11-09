package com.example.kidvkid.client;

import com.example.kidvkid.KidVKid;
import com.example.kidvkid.client.render.entity.*;
import com.example.kidvkid.init.ModEntityTypes;
import com.example.kidvkid.init.ModParticles;
import com.example.kidvkid.particle.SmokeTrailParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Subscribe to events from the MOD EventBus that should be handled on the PHYSICAL CLIENT side in this class
 *
 * @author Cadiboo
 */
@EventBusSubscriber(modid = KidVKid.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger(KidVKid.MODID + " Client Mod Event Subscriber");

    /**
     * We need to register our renderers on the client because rendering code does not exist on the server
     * and trying to use it on a dedicated server will crash the game.
     * <p>
     * This method will be called by Forge when it is time for the mod to do its client-side setup
     * This method will always be called after the Registry events.
     * This means that all Blocks, Items, TileEntityTypes, etc. will all have been registered already
     */
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        // Register Entity Renderers
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GIANT_WITHER.get(), GiantWitherRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BACON_ENTITY.get(), BaconEntityRenderer::new);


        LOGGER.debug("Registered Entity Renderers");
    }
    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
        ModParticles.SMOKE_TRAIL.setRegistryName(KidVKid.MODID, "smoke_trail");
        event.getRegistry().register(ModParticles.SMOKE_TRAIL);
    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.SMOKE_TRAIL, SmokeTrailParticle.Factory::new);
    }
}
