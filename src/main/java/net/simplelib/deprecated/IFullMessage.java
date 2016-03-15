package net.simplelib.deprecated;

/**
 * @author ci010
 */
public interface IFullMessage<Self extends IFullMessage<Self>> extends IServerMessage<Self>, IClientMessage<Self>
{
}
