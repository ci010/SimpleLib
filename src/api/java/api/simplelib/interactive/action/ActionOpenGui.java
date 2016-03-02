package api.simplelib.interactive.action;

import api.simplelib.IContainerProvider;
import api.simplelib.interactive.Interactive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.simplelib.HelperMod;
import net.simplelib.network.GuiHandler;

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
	public void interactWith(EntityPlayer player, BlockPos pos)
	{
		player.openGui(HelperMod.instance, guiId, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
	}
}
