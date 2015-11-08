package net.ci010.minecrafthelper;

import net.minecraft.entity.Entity;

/**
 * @author ci010
 */
public class EntityRegistryCache extends RegisterCache<EntityRegistryCache.EntityRegisterInfo>
{
	private static EntityRegistryCache instance;

	public static EntityRegistryCache instance()
	{
		if (instance == null)
			instance = new EntityRegistryCache();
		return instance;
	}

	public static class EntityRegisterInfo
	{
		Class<? extends Entity> clz;
		Object mod;
		String name;
		int id;
		int range;
		int frequnce;
		boolean velocity;

		public EntityRegisterInfo(Class<? extends Entity> clz, Object mod, String name, int id, int range, int frequnce,
								  boolean
										  velocity)
		{
			this.clz = clz;
			this.mod = mod;
			this.name = name;
			this.id = id;
			this.range = range;
			this.frequnce = frequnce;
			this.velocity = velocity;
		}
	}
}
