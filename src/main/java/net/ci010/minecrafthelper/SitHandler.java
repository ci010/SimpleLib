package net.ci010.minecrafthelper;

import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.annotation.Handler;
import net.ci010.minecrafthelper.entity.EntitySitableTemp;
import net.ci010.minecrafthelper.network.PlayerSitMessage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Set;

/**
 * @author CI010
 */
@Handler
public class SitHandler
{
	private static Set<Block> registered = Sets.newHashSet();

	static void register(Block block)
	{
		registered.add(block);
	}

	public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, EnumFacing face)
	{
		if (!existingEntity(world, x, y, z, player))
		{
			if (!world.isRemote)
			{
				EntitySitableTemp nemb = new EntitySitableTemp(world, x, y, z);
				world.spawnEntityInWorld(nemb);
				player.mountEntity(nemb);
				switch (face)
				{
					case DOWN:
						break;
					case UP:
						break;
					case NORTH:
						player.setAngles(180, player.rotationPitch);
						break;
					case SOUTH:
						break;
					case WEST:
						break;
					case EAST:
						break;
				}
			}
		}
		return true;
	}

	public static boolean existingEntity(World world, double x, double y, double z, EntityPlayer player)
	{
		@SuppressWarnings("unchecked")
		List<EntitySitableTemp> listEMB = world.getEntitiesWithinAABB(EntitySitableTemp.class,
				new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D,
						1D,
						1D));

		for (EntitySitableTemp mount : listEMB)
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
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
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !event.entityPlayer.isUsingItem())
			if (registered.contains(event.world.getBlockState(event.pos).getBlock()))
				ModNetwork.instance().sendToServer(new PlayerSitMessage(event.pos, event.face));
	}

	@SubscribeEvent
	public void onMount(EntityMountEvent event)
	{
		if (event.entityMounting instanceof EntityPlayer && event.entityBeingMounted instanceof EntitySitableTemp)
		{
			if (event.isDismounting())
				if (!event.entity.worldObj.isRemote && event.entityBeingMounted != null)
					event.entityBeingMounted.setDead();
		}
	}
}
