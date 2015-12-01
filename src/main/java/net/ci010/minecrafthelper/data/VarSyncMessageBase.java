package net.ci010.minecrafthelper.data;

import net.ci010.minecrafthelper.ModNetwork;
import net.ci010.minecrafthelper.abstracts.UpdateSafe;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author ci010
 */
public abstract class VarSyncMessageBase<T> extends VarSync<T> implements UpdateSafe
{
	@SideOnly(Side.SERVER)
	protected Listener listeners;

	public VarSyncMessageBase(Listener listeners)
	{
		this.listeners = listeners;
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

	public void setListener(Listener listener)
	{
		this.listeners = listener;
	}

	public interface Listener
	{
		List<EntityPlayerMP> getListeners();
	}
}
