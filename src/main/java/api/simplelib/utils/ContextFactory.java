package api.simplelib.utils;

import api.simplelib.Context;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class ContextFactory
{
	public static Context newContext(World world)
	{
		return new ContextWorld(world);
	}

	public static Context newContext(World world, BlockPos pos)
	{
		return new ContextWorldPos(world, pos);
	}

	public static class ContextWorld implements Context
	{
		private World world;

		public ContextWorld(World world)
		{
			this.world = world;
		}

		@Override
		public World getWorld()
		{
			return world;
		}

		@Override
		public BlockPos getPos()
		{
			return null;
		}

		@Override
		public String getId()
		{
			return "world";
		}
	}

	static class ContextWorldPos extends ContextWorld
	{
		private BlockPos pos;

		public ContextWorldPos(World world, BlockPos pos)
		{
			super(world); this.pos = pos;
		}

		@Override
		public BlockPos getPos()
		{
			return pos;
		}

		@Override
		public String getId()
		{
			return super.getId().concat("-pos");
		}
	}
}
