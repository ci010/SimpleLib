package net.ci010.minecrafthelper.data;

import net.ci010.minecrafthelper.abstracts.MinecraftComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ci010
 */
public class ComponentRegistry
{
	public void register(MinecraftComponent<?> component)
	{
		Object o = component.getComponent();
		if (o instanceof Item)
		{
			Item itm = (Item) o;
			GameRegistry.registerItem(itm, itm.getUnlocalizedName().substring(5));
		}
		else if (o instanceof Block)
		{

		}
		else
		{//TODO log

		}
	}
}
