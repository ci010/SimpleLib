package api.simplelib.network.obj;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * The message dispatcher of a {@link MessageType}.
 *
 * @author ci010
 */
public interface MessageDispatcher<T>
{
	MessageType<T> getMessageType();

	/**
	 * Send to all the player
	 * @param data
	 */
	void sendToAll(T data);

	void sendTo(T data, EntityPlayerMP player);

	void sendToAllAround(T data, NetworkRegistry.TargetPoint point);

	void sendToDimension(T data, int dimensionId);

	void sendToServer(T data);
}
