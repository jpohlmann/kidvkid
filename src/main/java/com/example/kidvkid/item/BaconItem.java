package com.example.kidvkid.item;

import com.example.kidvkid.entity.BaconEntity;
import com.example.kidvkid.init.ModItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaconItem extends Item {
    /**
     * Default constructor
     */
    public BaconItem() {
        super(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP));
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn,
                                     LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            player.heal(1);
        }
        return livingEntity.onFoodEaten(worldIn, stack);
    }

    public AbstractArrowEntity createBacon(World worldIn, ItemStack stack, LivingEntity shooter) {
        BaconEntity baconentity = new BaconEntity(worldIn, shooter);
        return baconentity;
    }
}
