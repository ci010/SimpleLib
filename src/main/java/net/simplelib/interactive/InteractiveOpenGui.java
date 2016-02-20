package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.simplelib.HelperMod;
import net.simplelib.network.GuiHandler;

/**
 * @author ci010
 */
public class InteractiveOpenGui implements Interactive
{
	private String id;
	private int guiId;
	private GuiHandler.ContainerProvider provider;

	public InteractiveOpenGui(String id, GuiHandler.ContainerProvider provider)
	{
		this.id = id;
		this.provider = provider;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void interactWith(EntityPlayer player, BlockPos pos)
	{
		player.openGui(HelperMod.instance, guiId, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void build()
	{
		this.guiId = GuiHandler.addContainerProvider(provider);
	}
}
