package net.ci010.minecrafthelper.deprecated;

import net.ci010.minecrafthelper.ModNetwork;
import net.ci010.minecrafthelper.data.VarSync;
import net.ci010.minecrafthelper.network.NBTWindowsMessage;

import java.util.List;

/**
 * @author ci010
 */
public class SyncPortalClient extends SyncPortal
{
	public SyncPortalClient(int id, List<VarSync> serils)
	{
		super(id, serils);
	}

	@Override
	void sendChange(int winId, int id, VarSync seril)
	{
		ModNetwork.instance().sendTo(new NBTWindowsMessage(winId, id, seril.getData()));
	}
}
