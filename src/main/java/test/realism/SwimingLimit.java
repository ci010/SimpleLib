package test.realism;

import api.simplelib.common.ModHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * @author ci010
 */
@ModHandler
public class SwimingLimit
{
	int nextUpTime = 0, upTime = 0;

	boolean isFullInWater(EntityPlayer player)
	{
		return player.worldObj.getBlockState(new BlockPos((int) player.posX, (int) player.posY + 1, (int) player.posZ)).getBlock().getMaterial() == Material.water;
	}

	boolean isWaterUnder(EntityPlayer player)
	{
		return player.worldObj.getBlockState(new BlockPos(player.posX, player.posY - 1, player.posZ)).getBlock().getMaterial() == Material.water;
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (!(event.entityLiving instanceof EntityPlayer))
			return;
		if (event.entityLiving.isInWater())
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			Random r = player.getRNG();
			if (isFullInWater(player) || isWaterUnder(player))
			{
				if (nextUpTime <= 0)
					upTime = r.nextInt(32);
				if (upTime > 0)
				{
					upTime--;
					if (upTime == 0)
						nextUpTime = r.nextInt(32) + 32;
				}
				if (nextUpTime > 0)
				{
					player.moveEntity(0, -0.1, 0);
					nextUpTime--;
				}
			}
		}
	}
}
