package com.example.kidvkid.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RailgunItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();
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
        ItemStack itemstack = playerentity.getHeldItem(handIn);
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
                    abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                    abstractarrowentity = customeArrow(abstractarrowentity);
                    if (f == 1.0F) {
                        abstractarrowentity.setIsCritical(true);
                    }

                    itemstack.damageItem(1, playerentity, (p_220009_1_) -> {
                        p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                    });
                    if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                        abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    }

                    worldIn.addEntity(abstractarrowentity);
                }

                worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
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
             return ActionResult.resultFail(itemstack);
         } else {
             playerentity.setActiveHand(handIn);
             LOGGER.info("Bacon success!!");
             return ActionResult.resultConsume(itemstack);
         }

     }

    public AbstractArrowEntity customeArrow(AbstractArrowEntity arrow) {
        return arrow;
    }
}
