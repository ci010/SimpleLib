package api.simplelib.network.obj;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
public interface MessageContext
{
	<T> void response(MessageType<T> handler, T data);

	EntityPlayer getPlayer();

	World getWorld();

	INetHandler getNetHandler();

	Side getSide();
}
