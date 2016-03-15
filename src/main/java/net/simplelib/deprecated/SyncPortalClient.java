package net.simplelib.deprecated;

import api.simplelib.VarSync;

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
//		ModNetwork.instance().sendTo(new NBTWindowsMessage(winId, id, seril.get()));
	}
}
