package net.simplelib.server;

import api.simplelib.registry.ModConfig;
import api.simplelib.registry.ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
@ModHandler
public class SpawnCheck
{
	@ModConfig(categoryId = "entity_spawn", id = "spawn_distance")
	public static int distance = 60;

	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent event)
	{
		for(EntityPlayer entity : event.getWorld().playerEntities)
			if (entity.posX - event.getX() < distance &&
					entity.posY - event.getY() < distance &&
					entity.posZ - event.getZ() < distance)
				return;
		event.setCanceled(true);
	}
}
