package net.simplelib.deprecated;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayerMP;
import api.simplelib.VarSync;

import java.util.List;

/**
 * @author ci010
 */
@Deprecated
public class SyncPortalServer extends SyncPortal
{
//	private List<EntityPlayerMP> listener;
//
//	public SyncPortalServer(int id, List<VarSync> data)
//	{
//		super(id, data);
//	}
//
//	public void addListner(EntityPlayerMP playerMP)
//	{
//		if (listener == null)
//			listener = Lists.newArrayList();
//		this.listener.add(playerMP);
//	}
//
//	public void removeListener(EntityPlayerMP entityPlayer)
//	{
//		if (listener != null)
//			listener.remove(entityPlayer);
//	}
//
//	@Override
//	protected void sendChange(int winId, int id, VarSync seril)
//	{
////		for (EntityPlayerMP playerMP : listener)
////			ModNetwork.instance().sendTo(new NBTWindowsMessage(winId, id, seril.get()), playerMP);
//	}
}
