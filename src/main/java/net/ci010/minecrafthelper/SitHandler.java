package net.ci010.minecrafthelper;

import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.annotation.Handler;
import net.ci010.minecrafthelper.entity.EntitySitableTemp;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.List;
import java.util.Set;

/**
 * @author CI010
 */
@Handler
class SitHandler
{
	private static Set<Block> registered = Sets.newHashSet();

	static void register(Block block)
	{
		registered.add(block);
	}

	public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double par6)
	{
		if (!checkForExistingEntity(world, x, y, z, player))
		{
			if (!world.isRemote)
			{
				EntitySitableTemp nemb = new EntitySitableTemp(world, x, y, z, par6);
				world.spawnEntityInWorld(nemb);
				player.mountEntity(nemb);
//				world.getEntities()
//				ModNetwork.instance().sendTo();
			}
		}
		return true;
	}

	public static boolean sitOnBlock(World world, BlockPos pos, EntityPlayer player, double par6)
	{
		return sitOnBlock(world, pos.getX(), pos.getY(), pos.getZ(), player, par6);
	}

	public static boolean checkForExistingEntity(World world, double x, double y, double z, EntityPlayer player)
	{
		@SuppressWarnings("unchecked")
		List<EntitySitableTemp> listEMB = world.getEntitiesWithinAABB(EntitySitableTemp.class,
				new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D,
						1D,
						1D));

		for (EntitySitableTemp mount : listEMB)
			if (mount.posX == x && mount.posY == y && mount.posZ == z)
			{
				if (mount.riddenByEntity == null)
					player.mountEntity(mount);
				return true;
			}
		return false;
	}

	@SubscribeEvent
	public void onBlockActive(PlayerInteractEvent event)
	{
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			if (registered.contains(event.world.getBlockState(event.pos).getBlock()))
				sitOnBlock(event.world, event.pos, event.entityPlayer, 0.5);
	}

	@SubscribeEvent
	public void onMount(EntityMountEvent event)
	{
		if (event.entityMounting instanceof EntityPlayer && event.entityBeingMounted instanceof EntitySitableTemp)
		{
			if (event.isDismounting())
			{
				if (event.entityBeingMounted != null)
					event.entityBeingMounted.setDead();
				else
					System.out.println("fuck fml, the sitable block lost!");
			}
			else
			{
				if (event.entityBeingMounted.riddenByEntity != event.entityMounting)
					event.entityBeingMounted.riddenByEntity = event.entityMounting;
				if (event.entityMounting.ridingEntity != event.entityBeingMounted)
					event.entityMounting.ridingEntity = event.entityBeingMounted;
			}
		}
	}
}
