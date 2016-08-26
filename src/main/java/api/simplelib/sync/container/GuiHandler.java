package api.simplelib.sync.container;

import api.simplelib.registry.ModGuiHandler;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.simplelib.HelperMod;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author ci010
 */
@ModGuiHandler
public class GuiHandler implements IGuiHandler
{
	private static List<IContainerProvider> providers = Lists.newArrayList();

	public static GuiBootStrap createFrom(IContainerProvider provider)
	{
		if (providers.contains(provider))
			return new GBS(providers.indexOf(provider));
		providers.add(provider);
		return new GBS(providers.size() - 1);
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


	private static class GBS implements GuiBootStrap
	{
		int id;

		public GBS(int id)
		{
			this.id = id;
		}

		//		@Override
		public void openGui(EntityPlayer player, BlockPos targetPosition)
		{
			if (targetPosition == null)
				targetPosition = player.getPosition();
			player.openGui(HelperMod.instance, id, player.worldObj, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
		}

		@Override
		public void openGui(@Nonnull EntityPlayer player, ICapabilityProvider provider, EnumFacing facing)
		{
		}
	}
}
