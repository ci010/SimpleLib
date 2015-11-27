package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.ModNetwork;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
public abstract class VarSyncMessageBase<T> extends VarSync<T>
{
	public VarSyncMessageBase(Listener listeners)
	{
		super(listeners);
	}

	public void setFromEvent()
	{

	}

	@Override
	public void update()
	{
		if (side == Side.SERVER)
			for (EntityPlayerMP entityPlayerMP : listeners.getListeners())
				ModNetwork.instance().sendTo(this.getIMessage(), entityPlayerMP);
		else
			ModNetwork.instance().sendTo(this.getIMessage());
	}

	public abstract IMessage getIMessage();
}
