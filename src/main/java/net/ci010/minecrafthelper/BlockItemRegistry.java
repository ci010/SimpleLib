package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.annotation.Construct;
import net.ci010.minecrafthelper.annotation.OreDic;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.ci010.minecrafthelper.data.StructBlock;
import net.ci010.minecrafthelper.data.StructItem;
import net.ci010.minecrafthelper.abstracts.ArgumentHelper;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static net.ci010.minecrafthelper.HelperMod.LOG;

class BlockItemRegistry
{
	private static BlockItemRegistry instance;

	static BlockItemRegistry instance() {if (instance == null) instance = new BlockItemRegistry(); return instance;}

	Map<Class<? extends Annotation>, ArgumentHelper> map = Maps.newHashMap();

	ModelHelper model = new ModelHelper();

	private Maker<Block> blockMaker = new Maker<Block>()
	{
		@Override
		BlockItemStruct warpStruct(Block target)
		{
			return new StructBlock(target);
		}
	};

	private Maker<Item> itemMaker = new Maker<Item>()
	{
		@Override
		BlockItemStruct warpStruct(Item target)
		{
			return new StructItem(target);
		}
	};

	private Maker<BlockItemStruct> maker = new Maker<BlockItemStruct>()
	{
		@Override
		BlockItemStruct warpStruct(BlockItemStruct target)
		{
			return target;
		}
	};

	abstract class Maker<Type>
	{
		@SuppressWarnings("unchecked")
		BlockItemStruct make(Field f)
		{
			Type item = null;
			Construct ctr = f.getAnnotation(Construct.class);

			if (ctr != null)
			{
				Object[] args = new Object[]{};

				for (Annotation a : f.getAnnotations())
					if (map.containsKey(a.annotationType()))
					{
						args = map.get(a.annotationType()).getArguments(a);
						break;
					}
				int length = args.length;
				Class<?>[] argType = new Class<?>[length];

				for (int i = 0; i < length; i++)
				{
					Class<?> c = args[i].getClass();
					if (c.equals(Float.class))
						c = float.class;
					else if (c.equals(Integer.class))
						c = int.class;
					else if (c.equals(Boolean.class))
						c = boolean.class;
					else if (c.equals(Double.class))
						c = double.class;
					else if (c.equals(Long.class))
						c = long.class;
					else if (c.equals(Short.class))
						c = short.class;
					argType[i] = c;
				}
				try
				{
					item = ((Class<? extends Type>) ctr.value()).getConstructor(argType).newInstance(args);
				}
				catch (InstantiationException e1)
				{
					e1.printStackTrace();
				}
				catch (IllegalAccessException e1)
				{
					e1.printStackTrace();
				}
				catch (IllegalArgumentException e1)
				{
					e1.printStackTrace();
				}
				catch (InvocationTargetException e1)
				{
					e1.printStackTrace();
				}
				catch (NoSuchMethodException e1)
				{
					for (Object o : args)
						LOG.fatal(o);
					if (args.length == 0)
						LOG.fatal("args is NULL!!!");
					for (Class<?> c : argType)
						LOG.fatal(c.getName());
					LOG.fatal("not found constructor in {} ", ctr.value().getName());
				}
				catch (SecurityException e1)
				{
					e1.printStackTrace();
				}
			}
			else
				try
				{
					item = (Type) f.get(null);
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}

			if (item == null)
			{
				LOG.fatal("Item Field {}'s value is null. It will not be registied!", f.getName());
				return null;
			}

			try
			{
				f.set(null, item);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return this.warpStruct(item);
		}

		abstract BlockItemStruct warpStruct(Type target);
	}

	BlockItemStruct parseField(Field f)
	{
		switch (getType(f))
		{
			case Item:
				return itemMaker.make(f);
			case Block:
				return blockMaker.make(f);
			case Custom:
				return maker.make(f);
			case Exception:
				LOG.error(f.getType() + " is in " + this.getType(f) + " type");
			default:
				break;
		}
		return null;
	}

	void process(ContainerMeta meta)
	{
		for (Field f : meta.getFields())
		{
			BlockItemStruct data = parseField(f);

			if (data == null)
				continue;

			data.setName(f.getName());
			OreDic anno = f.getAnnotation(OreDic.class);

			if (data.blocks() != null)
				for (Block block : data.blocks())
				{
					if (anno != null)
						OreDictionary.registerOre(anno.value().isEmpty() ? block.getUnlocalizedName().substring(5) : anno
										.value(),
								block);
					GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
					if (HelperMod.proxy.isClient())
						model.registerBlock(block, block.getUnlocalizedName().substring(5));
				}
			if (data.items() != null)
				for (Item item : data.items())
				{
					if (anno != null)
						OreDictionary.registerOre(anno.value().isEmpty() ? item.getUnlocalizedName().substring(5) : anno
										.value(),
								item);
					GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
					if (HelperMod.proxy.isClient())
						model.registerItem(item, item.getUnlocalizedName().substring(5));
				}
			if (HelperMod.proxy.isClient())
			{
				if (meta.needModel() || meta.needLang())
				{
					FileGenerator g = new FileGenerator();
					if (meta.needModel())
						try
						{
							g.model(data);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					if (meta.needLang())
					{
						g.setLangType(meta.langType());
						g.lang(data);
						try
						{
							g.writeLang();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
		if (HelperMod.proxy.isClient())
			model.registerBlock(block, block.getUnlocalizedName().substring(5));
	}

	public void registerItem(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		if (HelperMod.proxy.isClient())
			model.registerItem(item, item.getUnlocalizedName().substring(5));
	}

	public FieldType getType(Field f)
	{
		return this.getType(f.getType());
	}

	public FieldType getType(Class<?> c)
	{
		if (Item.class.isAssignableFrom(c))
			return FieldType.Item;
		if (Block.class.isAssignableFrom(c))
			return FieldType.Block;
		if (BlockItemStruct.class.isAssignableFrom(c))
			return FieldType.Custom;
		return FieldType.Exception;
	}


	public enum FieldType
	{
		Item, Block, Custom, Exception
	}

	void clear()
	{
		if (Loader.instance().getLoaderState() == LoaderState.AVAILABLE)
		{
			instance = null;
		}
	}
}
