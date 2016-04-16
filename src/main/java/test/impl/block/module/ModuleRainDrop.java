package test.impl.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.Module;
import test.api.component.block.module.RainDrop;
import test.api.world.World;

/**
 * @author ci010
 */
public class ModuleRainDrop extends Module<RainDrop>
{
	public static final ModuleRainDrop INSTANCE = new ModuleRainDrop();

	private ModuleRainDrop()
	{
		super("rain_drop", RainDrop.class, new ModuleContainerBase<RainDrop>()
		{
			@Override
			public RainDrop getContainer()
			{
				return new RainDrop()
				{
					@Override
					public void onRainDropOn(World worldIn, BlockPos pos)
					{
						for (RainDrop delegate : list)
							delegate.onRainDropOn(worldIn, pos);
					}
				};
			}
		});
	}
}
