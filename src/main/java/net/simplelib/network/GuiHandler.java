package net.simplelib.network;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.simplelib.common.registry.annotation.type.ModGuiHandler;

import java.util.List;

/**
 * @author ci010
 */
@ModGuiHandler
public class GuiHandler implements IGuiHandler
{
	private static List<ContainerProvider> providers = Lists.newArrayList();

	public static int addContainerProvider(ContainerProvider provider)
	{
		if (providers.contains(provider))
			return providers.indexOf(provider);
		providers.add(provider);
		return providers.size() - 1;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getContainer(player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getGuiContainer(player, world, x, y, z);
	}

	/**
	 * @author ci010
	 */
	public interface ContainerProvider
	{
		Container getContainer(EntityPlayer player, World world, int x, int y, int z);

		GuiContainer getGuiContainer(EntityPlayer player, World world, int x, int y, int z);
	}
}
