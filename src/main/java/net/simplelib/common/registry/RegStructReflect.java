package net.simplelib.common.registry;

import api.simplelib.component.Construct;
import api.simplelib.component.ComponentStruct;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.simplelib.common.registry.abstracts.RegComponentBase;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ci010
 */
public class RegStructReflect<T> extends RegComponentBase<T>
{
	private String unlocalizedName;
	private List<String> itemMeta;
	private List<String> blockMeta;
	private List<RegItem> items;
	private List<RegBlock> blocks;

	public RegStructReflect(T wrap)
	{
		super(wrap);
		this.itemMeta = Lists.newArrayList();
		this.blockMeta = Lists.newArrayList();
		this.items = Lists.newArrayList();
		this.blocks = Lists.newArrayList();
		Class<?> clz = wrap.getClass();
		try
		{
			this.discover(wrap, clz);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	private void discover(Object o, Class<?> clz) throws IllegalAccessException
	{
		Field[] declaredFields = clz.getDeclaredFields();
		for (Field f : declaredFields)
		{
			if (f.isAnnotationPresent(Construct.Ignore.class))
				continue;
			Class<?> type = f.getType();
			if (!f.isAccessible())
				f.setAccessible(true);
			Object o1 = f.get(o);
			if (o1 == null)
				continue;
			if (Block.class.isAssignableFrom(type))
			{
				blockMeta.add(f.getName());
				blocks.add(new RegBlock((Block) o1));
			}
			else if (Item.class.isAssignableFrom(type))
			{
				itemMeta.add(f.getName());
				items.add(new RegItem((Item) o1));
			}
			else
			{

			}
		}

		Class<?> superclass = clz.getSuperclass();
		if (superclass.isAnnotationPresent(ComponentStruct.class))
			discover(o, superclass);
	}

	@Override
	public RegComponentBase<T> setUnlocalizedName(String name)
	{
		this.unlocalizedName = name;
		for (int i = 0; i < itemMeta.size(); i++)
		{
			String shortName = name.concat(".").concat(itemMeta.get(i));
			items.get(i).setUnlocalizedName(shortName);
			itemMeta.set(i, shortName);
		}
		for (int i = 0; i < blockMeta.size(); i++)
		{
			String shortName = name.concat(".").concat(blockMeta.get(i));
			blocks.get(i).setUnlocalizedName(shortName);
			blockMeta.set(i, shortName);
		}
		return this;
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	@Override
	public RegComponentBase<T> setCreativeTab(CreativeTabs tab)
	{
		return null;
	}

	@Override
	public RegComponentBase<T> register(String name)
	{
		for (int i = 0; i < itemMeta.size(); i++)
			items.get(i).register(itemMeta.get(i));
		for (int i = 0; i < blockMeta.size(); i++)
			blocks.get(i).register(blockMeta.get(i));
		return this;
	}

	@Override
	public RegComponentBase<T> registerOre(String name)
	{
		//TODO consider about this
		return null;
	}

	@Override
	public RegComponentBase<T> registerModel(String name)
	{
		for (int i = 0; i < itemMeta.size(); i++)
			items.get(i).registerModel(itemMeta.get(i));
		for (int i = 0; i < blockMeta.size(); i++)
			blocks.get(i).registerModel(blockMeta.get(i));
		return this;
	}
}
