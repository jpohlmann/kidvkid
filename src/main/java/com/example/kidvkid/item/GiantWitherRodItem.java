package com.example.kidvkid.item;

import com.example.kidvkid.entity.GiantWitherEntity;
import com.example.kidvkid.init.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Summoning rod for giant wither mobs.  Swinging it will create one in the world.
 */
public class GiantWitherRodItem extends Item {
    /**
     * Default constructor
     *
     * @param builder
     */
    public GiantWitherRodItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * When we right click with this item, summon a giant wither where we clicked.
     *
     * @param worldIn
     * @param playerIn
     * @param handIn
     * @return
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        EntityType<?> entitytype = ModEntityTypes.GIANT_WITHER.get();
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
        BlockPos blockpos = blockraytraceresult.getPos();
        entitytype.spawn(worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWNER, false, false);
        return ActionResult.resultSuccess(itemstack);
    }
}
