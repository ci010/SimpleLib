package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.ci010.minecrafthelper.annotation.type.StaticComponent;
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

	public static void put(String id, Item item)
	{
		itemMap.put(id, item);
	}

	public static void put(String id, Block block)
	{
		blockMap.put(id, block);
	}

	public static void put(String modid, BlockItemStruct struct)
	{
		if (struct.blocks() != null)
			for (Block block : struct.blocks())
				blockMap.put(modid, block);
		if (struct.items() != null)
			for (Item item : struct.items())
				itemMap.put(modid, item);
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
