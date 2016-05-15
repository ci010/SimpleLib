package net.simplelib.world;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

/**
 * @author ci010
 */
public class AttachWorldCapEvent extends AttachCapabilitiesEvent
{
	private World world;

	public AttachWorldCapEvent(World world)
	{
		super(world);
		this.world = world;
	}

	public World getWorld()
	{
		return world;
	}
}
