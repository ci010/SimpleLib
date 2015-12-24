package net.simplelib.deprecated;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author CI010
 */
public class ComponentItem extends MinecraftComponent<Item>
{
	public ComponentItem(Item wrap)
	{
		super(wrap);
	}

	@Override
	public Item setUnlocalizedName(String name)
	{
		return this.getComponent().setUnlocalizedName(name);
	}

	@Override
	public Item setCreativeTab(CreativeTabs tab)
	{
		return this.getComponent().setCreativeTab(tab);
	}
}
