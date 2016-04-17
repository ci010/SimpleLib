package test.impl;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.simplelib.RegistryHelper;
import test.api.component.GameComponent;
import test.api.component.block.ComponentBlock;
import test.api.component.entity.ComponentEntity;
import test.api.component.item.ComponentItem;

import java.util.HashMap;

/**
 * @author ci010
 */
public class GameComponents
{
	private static HashMap<String, GameComponent> map = Maps.newHashMap();

	public static void register(String modid, GameComponent component)
	{
		if (component instanceof ComponentBlock)
			registerBlock(modid, (ComponentBlock) component);
		else if (component instanceof ComponentItem)
			registerItem((ComponentItem) component);
		else if (component instanceof ComponentEntity)
			registerEntity((ComponentEntity) component);
	}

	private static void registerBlock(String modid, ComponentBlock block)
	{
//		BlockCompiledInfo builder = new BlockCompiledInfo();
//		block.build(builder);
//		RegistryHelper.INSTANCE.registerBlock(modid, new BlockCompiled(block, builder), block.getId());
	}

	private static void registerItem(ComponentItem item)
	{

	}

	private static void registerEntity(ComponentEntity entity)
	{

	}

	public static ComponentEntity get(Entity entity)
	{
		return null;
	}

	public static ComponentBlock get(Block block)
	{
		return null;
	}

	public static ComponentItem get(Item item)
	{
		return null;
	}

	public static GameComponent get(String id)
	{
		return map.get(id);
	}
}
