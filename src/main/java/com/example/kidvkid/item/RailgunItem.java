package com.example.kidvkid.item;

import com.example.kidvkid.KidVKid;
import com.example.kidvkid.init.ModEntityTypes;
import com.example.kidvkid.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.function.Predicate;

public class RailgunItem extends ShootableItem {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final Predicate<ItemStack> BACON = (p_220002_0_) -> (p_220002_0_.getItem() instanceof BaconItem);

    /**
     * Default constructor
     *
     * @param builder
     */
    public RailgunItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
     public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerentity, Hand handIn) {
        ItemStack stack = playerentity.getHeldItem(handIn);

        ItemStack itemstack = playerentity.findAmmo(stack);
        boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;

        if (!itemstack.isEmpty() || flag) {
            if (itemstack.isEmpty()) {
                itemstack = new ItemStack(BaconItem::new);
            }

            float f = 100;
            if (!((double)f < 0.1D)) {
                boolean flag1 = playerentity.abilities.isCreativeMode;
                if (!worldIn.isRemote) {
                    BaconItem baconItem = (BaconItem) (itemstack.getItem() instanceof BaconItem ? itemstack.getItem() : new BaconItem());
                    LOGGER.info("Bacon created");
                    AbstractArrowEntity abstractarrowentity = baconItem.createBacon(worldIn, itemstack, playerentity);
                    abstractarrowentity.shoot(playerentity.getLookVec().x, playerentity.getLookVec().y, playerentity.getLookVec().z, 300F, 0F);
                    this.playBaconRailgunSound(worldIn, playerentity);
                    LOGGER.info("Bacon sound played");
                    abstractarrowentity = customeArrow(abstractarrowentity);
                    if (f == 1.0F) {
                        abstractarrowentity.setIsCritical(true);
                    }

                    itemstack.damageItem(1, playerentity, (p_220009_1_) -> {
                        p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                    });
                    worldIn.addEntity(abstractarrowentity);
                }
                if (!flag1 && !playerentity.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.deleteStack(itemstack);
                    }
                }
                playerentity.addStat(Stats.ITEM_USED.get(this));
            }

        }
         if (!playerentity.abilities.isCreativeMode && !flag) {
             return ActionResult.resultFail(stack);
         } else {
             return ActionResult.resultConsume(stack);
         }

     }
    private void playBaconRailgunSound(World worldIn, PlayerEntity player) {
        SoundEvent soundevent = new SoundEvent(new ResourceLocation("kidvkid", "bacon_railgun_sound"));
        worldIn.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), soundevent, SoundCategory.PLAYERS, 1F, 1.0F);
    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return BACON;
    }

    public AbstractArrowEntity customeArrow(AbstractArrowEntity arrow) {
        return arrow;
    }
}
