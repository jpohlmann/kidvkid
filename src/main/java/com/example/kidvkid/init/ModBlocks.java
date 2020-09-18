package com.example.kidvkid.init;

import com.example.kidvkid.KidVKid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Define the blocks for our mod
 *
 * @author Justin and Jackson Pohlmann
 */
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, KidVKid.MODID);
}
