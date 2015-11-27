package net.ci010.minecrafthelper.test;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author ci010
 */
public abstract class VarSync<T> extends Var<T>
{
	public final Side side = FMLCommonHandler.instance().getSide();
	protected boolean dirty;
	@SideOnly(Side.SERVER)
	protected Listener listeners;

	public boolean isDirty()
	{
		return this.dirty;
	}

	public void setData(T data)
	{
		this.dirty = true;
		super.setData(data);
	}

	public VarSync(Listener listeners)
	{
		this.listeners = listeners;
	}

	public interface Listener
	{
		List<EntityPlayerMP> getListeners();

		int getVarId(VarSync var);
	}
}
