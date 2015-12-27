package net.simplelib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author ci010
 */
public class Namespace
{
	private String parent = null, name, oreName;
	private MinecraftComponent component;

	public Namespace(String name, MinecraftComponent component)
	{
		this.component = component;
		this.name = name;
	}

	public Namespace setParent(String parent)
	{
		this.parent = parent;
		return this;
	}

	public Namespace setOreName(String name)
	{
		oreName = name;
		return this;
	}

	public boolean needRegOre()
	{
		return oreName != null;
	}

	public String getOreName()
	{
		return oreName;
	}

	public String getParent()
	{
		return parent;
	}

	public String getName()
	{
		return name;
	}

	public MinecraftComponent getComponent()
	{
		return component;
	}

	@Override
	public String toString()
	{
		return parent == null ? name : parent.concat("_").concat(name);
	}

	public static Namespace newSpace(String name, Block block)
	{
		return new Namespace(name, new ComponentBlock(block));
	}

	public static Namespace newSpace(String name, Item block)
	{
		return new Namespace(name, new ComponentItem(block));
	}
}
