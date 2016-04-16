package api.simplelib.interactive.action;

import api.simplelib.Context;
import api.simplelib.container.IContainerProvider;
import api.simplelib.interactive.Interactive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.simplelib.HelperMod;
import api.simplelib.container.GuiHandler;

/**
 * @author ci010
 */
public class ActionOpenGui implements Interactive.Action
{
	private final int guiId;

	public ActionOpenGui(IContainerProvider provider)
	{
		this.guiId = GuiHandler.addContainerProvider(provider);
	}

	@Override
	public void interactWith(EntityPlayer player, Context context)
	{
		player.openGui(HelperMod.instance, guiId, context.getWorld(),
				context.getPos().getX(),
				context.getPos().getY(),
				context.getPos().getZ());
	}
}
