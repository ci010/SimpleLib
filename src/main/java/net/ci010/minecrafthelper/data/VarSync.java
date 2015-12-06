package net.ci010.minecrafthelper.data;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
public abstract class VarSync<T> extends Var<T>
{
	public final Side side = FMLCommonHandler.instance().getSide();
	protected boolean dirty;

	public boolean isDirty()
	{
		return this.dirty;
	}

	public void setData(T data)
	{
		this.dirty = true;
		super.setData(data);
	}
}
