package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.annotation.StaticComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * @author CI010
 */
public class ComponentsRepository
{
	static Map<String, Block> blockMap = Maps.newHashMap();
	static Map<String, Item> itemMap = Maps.newHashMap();

	static void put(String id, Item item)
	{
		itemMap.put(id, item);
	}

	static void put(String id, Block block)
	{
		blockMap.put(id, block);
	}

	public static Item getItem(String id)
	{
		return itemMap.get(id);
	}

	public static Block getBlock(String id)
	{
		return blockMap.get(id);
	}

	public static Item getItem(Class<? extends Item> clz)
	{
		if (clz.isAnnotationPresent(StaticComponent.class))
			return itemMap.get(clz.getName());
		else
			return null;
	}

	public static Block getBlock(Class<? extends Block> clz)
	{
		if (clz.isAnnotationPresent(StaticComponent.class))
			return blockMap.get(clz.getName());
		else
			return null;
	}

	static void process()
	{

	}
}
