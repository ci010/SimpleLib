package net.simplelib.interactive.cloud;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.simplelib.interactive.ContainerCommon;
import net.simplelib.interactive.Interactive;
import net.simplelib.interactive.InteractiveMetadata;

/**
 * @author ci010
 */
public class Cloud extends InteractiveMetadata
{
	public Cloud(Interactive info)
	{
		super(info);
	}

	@Override
	public Container getContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		return this.createEntity(world).loadToContainer(new ContainerCommon().loadPlayerSlot(player.inventory));
	}
}
