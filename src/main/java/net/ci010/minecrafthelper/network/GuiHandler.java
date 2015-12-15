package net.ci010.minecrafthelper.network;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.annotation.type.ModGuiHandler;
import net.ci010.minecrafthelper.interactive.ContainerProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

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
		if (!providers.contains(provider))
			providers.add(provider);
		return providers.indexOf(provider);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getContainer(ID, player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getGuiContainer(ID, player, world, x, y, z);
	}
}
