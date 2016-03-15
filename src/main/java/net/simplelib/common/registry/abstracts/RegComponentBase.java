package net.simplelib.common.registry.abstracts;

import net.minecraft.creativetab.CreativeTabs;

/**
 * @author CI010
 */
public abstract class RegComponentBase<T>
{
	private T component;

	public RegComponentBase(T wrap)
	{
		this.component = wrap;
	}

	public T getComponent()
	{
		return component;
	}

	public abstract RegComponentBase<T> setUnlocalizedName(String name);

	public abstract String getUnlocalizedName();

	public abstract RegComponentBase<T> setCreativeTab(CreativeTabs tab);

	public abstract RegComponentBase<T> register(String name);

	public abstract RegComponentBase<T> registerOre(String name);

	public abstract RegComponentBase<T> registerModel(String name);
}
