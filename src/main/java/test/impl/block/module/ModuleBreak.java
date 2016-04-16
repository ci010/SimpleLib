package test.impl.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.Module;
import test.api.component.block.StateBlock;
import test.api.component.block.module.Break;
import test.api.world.World;

/**
 * @author ci010
 */
public class ModuleBreak extends Module<Break>
{
	public static final ModuleBreak INSTANCE = new ModuleBreak();

	private ModuleBreak()
	{
		super("break", Break.class, new ModuleContainerBase<Break>()
		{
			@Override
			public Break getContainer()
			{
				return new Break()
				{
					@Override
					public void onBlockBreak(World world, BlockPos pos, StateBlock state)
					{
						for (Break b : list)
							b.onBlockBreak(world, pos, state);
					}
				};
			}
		});
	}
}
