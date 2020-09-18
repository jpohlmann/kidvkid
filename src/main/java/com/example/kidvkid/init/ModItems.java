package com.example.kidvkid.init;

import com.example.kidvkid.KidVKid;
import com.example.kidvkid.item.GiantWitherRodItem;
import com.example.kidvkid.item.ModdedSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link Item}s.
 * Suppliers that create Items are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the Item Registry Event is fired by Forge and it is time for the mod to
 * register its Items, our Items are created and registered by the DeferredRegister.
 * The Item Registry Event will always be called after the Block registry is filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, KidVKid.MODID);

    // This is a very simple Item. It has no special properties except for being on our creative tab.
    public static final RegistryObject<ModdedSpawnEggItem> GIANT_WITHER_SPAWN_EGG = ITEMS.register("giant_wither_spawn_egg", () -> new ModdedSpawnEggItem(ModEntityTypes.GIANT_WITHER, 0xF0A5A2, 0xA9672B, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
    public static final RegistryObject<GiantWitherRodItem> GIANT_WITHER_ROD = ITEMS.register("giant_wither_rod", () -> new GiantWitherRodItem(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

}