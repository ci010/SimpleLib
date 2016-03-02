package net.simplelib.interactive;

import api.simplelib.interactive.BlockInteractive;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.BaseHandler;
import api.simplelib.interactive.base.BlockBase;
import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.interactive.process.ProcessPipeline;
import net.minecraft.block.Block;
import net.simplelib.RegistryHelper;
import test.api.BlockAdapterInteractive;

/**
 * @author ci010
 */
public class BaseHandlerBlock implements BaseHandler
{
	@Override
	public void handle(Interactive interactive)
	{
		Interactive.Base base = interactive.getBase();
		if (base instanceof BlockBase)//just make sure this
		{
			BlockInteractive block = ((BlockBase) base).get();
			Block target;
			if (interactive instanceof ProcessPipeline || interactive instanceof Inventory)
				target = new BlockAdapterInteractive.Tile(block, interactive);
			else
				target = new BlockAdapterInteractive(block, interactive);
			RegistryHelper.INSTANCE.registerBlock(block.getModId(), target, block.getName());
			//TODO think about this.
		}
	}
}
