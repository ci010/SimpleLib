package net.simplelib.sitting;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.registry.annotation.type.Handler;
import net.simplelib.common.utils.GenericUtil;
import net.simplelib.network.ModNetwork;

import java.util.List;
import java.util.Map;

/**
 * @author CI010
 */
@Handler
public class SitHandler
{
	private static Map<Block, SittingMetaInfo> regMap = Maps.newConcurrentMap();

	public static void register(Block block, Situation situation)
	{
		if (block != null)
		{
			PropertyDirection dir = null;
			for (Object o : block.getDefaultState().getProperties().entrySet())
			{
				Map.Entry<Object, Object> entry = GenericUtil.cast(o);
				if (entry.getKey() instanceof PropertyDirection)
					dir = (PropertyDirection) entry.getKey();
			}
			regMap.put(block, new SittingMetaInfo(dir, situation));
		}
	}

	public static void register(Block block)
	{
		register(block, SituationDefault.instance());
	}

	public static void sitOnBlock(World world, BlockPos pos, EntityPlayer player)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		SittingMetaInfo meta = regMap.get(block);
		if (meta.situation.shouldSit(player, block))
		{
			double x = pos.getX(), y = pos.getY() + meta.situation.offsetVertical(),
					z = pos.getZ();
			if (meta.dir != null)
			{
				EnumFacing face = (EnumFacing) state.getValue(meta.dir);
				switch (face)
				{
					case DOWN:
						break;
					case UP:
						break;
					case NORTH:
						z += meta.situation.offsetHorizontal();
						player.rotationYaw  = 0;/* (180f, player.rotationPitch);*/
						break;
					case SOUTH:
						z -= meta.situation.offsetHorizontal();
						player.rotationYaw  = 180;
						break;
					case WEST:
						x -= meta.situation.offsetHorizontal();
						player.rotationYaw  = 270;
						break;
					case EAST:
						x += meta.situation.offsetHorizontal();
						player.rotationYaw  = 90;
						break;
				}
			}
			if (!existingEntity(world, x, y, z, player))
				if (!world.isRemote)
				{
					EntitySeat nemb = new EntitySeat(world, x, y, z);
					world.spawnEntityInWorld(nemb);
					player.mountEntity(nemb);
				}
		}
	}

	public static boolean existingEntity(World world, double x, double y, double z, EntityPlayer player)
	{
		@SuppressWarnings("unchecked")
		List<EntitySeat> listEMB = world.getEntitiesWithinAABB(EntitySeat.class,
				new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D,
						1D,
						1D));

		for (EntitySeat mount : listEMB)
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
		Block block;
		Situation situation;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			if (regMap.containsKey(block = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock()))
				if ((situation = regMap.get(block).situation).shouldSit(event.entityPlayer, block))
					ModNetwork.instance().sendToServer(new PlayerSitMessage(situation.offsetVertical(), event.pos));
	}

	@SubscribeEvent
	public void onMount(EntityMountEvent event)
	{
		if (event.entityMounting instanceof EntityPlayer && event.entityBeingMounted instanceof EntitySeat)
			if (event.isDismounting())
				if (!event.entity.worldObj.isRemote)
					event.entityBeingMounted.setDead();
	}

	private static class SittingMetaInfo
	{
		PropertyDirection dir;
		Situation situation;

		public SittingMetaInfo(PropertyDirection dir, Situation situation)
		{
			this.dir = dir;
			this.situation = situation;
		}
	}

	public interface Situation
	{
		boolean shouldSit(EntityPlayer player, Block block);

		float offsetVertical();

		float offsetHorizontal();
	}
}
