package net.simplelib.common.registry.abstracts;

import net.minecraft.creativetab.CreativeTabs;

/**
 * @author CI010
 */
public abstract class RegComponent<T>
{
	private T component;

	public RegComponent(T wrap)
	{
		this.component = wrap;
	}

	public T getComponent()
	{
		return component;
	}

	public abstract T setUnlocalizedName(String name);

	public abstract String getUnlocalizedName();

	public abstract T setCreativeTab(CreativeTabs tab);

	public abstract T register(String name);

	public abstract T registerOre(String name);

	public abstract T registerModel(String name);
}
