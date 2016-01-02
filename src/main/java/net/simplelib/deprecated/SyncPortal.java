package net.simplelib.deprecated;

import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public abstract class SyncPortal implements VarSync.Listener
{
	protected int id;
	protected List<VarSync> data;

	public SyncPortal(int id, List<VarSync> data)
	{
		this.id = id;
		this.data = data;
		for (VarSync varSync : data)
			varSync.addListener(this);
	}

	@Override
	protected void finalize() throws Throwable
	{
		for (VarSync varSync : data)
			varSync.removeListener(this);
		super.finalize();
	}

	public void loadFromNBT(int id, NBTTagCompound nbt)
	{
		this.data.get(id).get().readFromNBT(nbt);
	}

	@Override
	public void onChanged(VarSync var)
	{
		this.sendChange(this.id, data.indexOf(var), var);
	}

	abstract void sendChange(int winId, int id, VarSync seril);
}
