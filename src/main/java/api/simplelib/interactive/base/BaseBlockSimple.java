package api.simplelib.interactive.base;

import api.simplelib.interactive.BlockInteractive;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.wrapper.BaseHandler;
import api.simplelib.interactive.base.wrapper.BlockBaseWrapper;
import api.simplelib.interactive.base.wrapper.ModInteractiveBaseWrapper;
import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.interactive.process.ProcessPipeline;
import net.minecraft.block.Block;
import net.simplelib.RegistryHelper;
import net.simplelib.interactive.InteractiveMetadata;
import test.api.BlockAdapterInteractive;

/**
 * @author ci010
 */
public class BaseBlockSimple implements Interactive.Base
{
	private BlockInteractive block;

	public BaseBlockSimple(BlockInteractive blockInteractive)
	{
		this.block = blockInteractive;
	}

	@Override
	public void setup(Interactive interactive, Interactive.MetaData metaData)
	{
		Block target;
		if (!metaData.workers().isEmpty())
			target = new BlockAdapterInteractive.Tile(block, interactive);
		else
			target = new BlockAdapterInteractive(block, interactive);
		String name = block.getUnlocalizedName();
		if (name.equals("tile."))
		{
			name = interactive.getId().concat("_block");
			block.setUnlocalizedName(name);
		}
		else
		{
			name = name.substring(5);
		}
		RegistryHelper.INSTANCE.registerBlock(block.getModId(), target, name);
	}
}
