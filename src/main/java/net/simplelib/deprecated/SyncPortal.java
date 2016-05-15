package net.simplelib.deprecated;

import api.simplelib.VarSyncBase;

import java.util.List;

/**
 * @author ci010
 */
@Deprecated
public abstract class SyncPortal// implements VarSync.Listener
{
	protected int id;
	protected List<VarSyncBase> data;

//	public SyncPortal(int id, List<VarSync> data)
//	{
//		this.id = id;
//		this.data = data;
//		for (VarSync varSync : data)
//			varSync.addListener(this);
//	}
//
//	@Override
//	protected void finalize() throws Throwable
//	{
//		for (VarSync varSync : data)
//			varSync.removeListener(this);
//		super.finalize();
//	}
//
//	public void loadFromNBT(int id, NBTTagCompound nbt)
//	{
//		this.data.get(id).writeToNBT(nbt);
//	}
//
//	@Override
//	public void onChanged(VarSync var)
//	{
//		this.sendChange(this.id, data.indexOf(var), var);
//	}
//
//	abstract void sendChange(int winId, int id, VarSync seril);
}
