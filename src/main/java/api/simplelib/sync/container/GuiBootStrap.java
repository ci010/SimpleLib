package api.simplelib.sync.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public interface GuiBootStrap
{
	void openGui(@Nonnull EntityPlayer player, ICapabilityProvider provider, EnumFacing facing);
}
