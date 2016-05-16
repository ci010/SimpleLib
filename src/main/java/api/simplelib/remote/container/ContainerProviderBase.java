package api.simplelib.remote.container;

import api.simplelib.gui.GuiContainerCommon;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class ContainerProviderBase implements IContainerProvider
{
	private boolean loadPlayer = true;

	public ContainerProviderBase setLoadPlayerInventory(boolean loadPlayerInventory)
	{
		this.loadPlayer = loadPlayerInventory;
		return this;
	}

	@Override
	public Container getContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		ContainerBase base = new ContainerBase().load(world, world.getTileEntity(new BlockPos(x, y, z)));
		if (loadPlayer)
			base.loadPlayer(player);
		return base;
	}

	@Override
	public GuiScreen getGuiContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		ContainerBase base = new ContainerBase();
		return new GuiContainerCommon(base);
	}
}
