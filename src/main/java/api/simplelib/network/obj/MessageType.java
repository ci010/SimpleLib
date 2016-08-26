package api.simplelib.network.obj;

import api.simplelib.seril.INBTSerializer;

/**
 * @author ci010
 */
public interface MessageType<T>
{
	void handleClient(MessageContext context, T obj);

	void handleServer(MessageContext context, T obj);

	INBTSerializer<T> getSerializer();
}
