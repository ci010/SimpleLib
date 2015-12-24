package net.simplelib.deprecated;

import net.simplelib.ModNetwork;
import net.simplelib.data.VarSync;
import net.simplelib.network.NBTWindowsMessage;

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
