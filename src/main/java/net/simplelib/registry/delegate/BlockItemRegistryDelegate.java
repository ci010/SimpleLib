package net.simplelib.registry.delegate;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.CommonLogger;
import net.simplelib.HelperMod;
import net.simplelib.RegistryHelper;
import net.simplelib.abstracts.ArgumentHelper;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.field.OreDic;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.BlockItemContainer;
import net.simplelib.annotation.type.BlockItemStruct;
import net.simplelib.registry.*;
import test.NameFormattor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ci010
 */
@ASMDelegate
public class BlockItemRegistryDelegate extends RegistryDelegate<BlockItemContainer>
{
	private ReflectionMaker<Block, ImmutableSet<Namespace>> blockMaker;
	private ReflectionMaker<Item, ImmutableSet<Namespace>> itemMaker;
	private ReflectionMaker<Object, ImmutableSet<Namespace>> maker;

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Map<Class<? extends Annotation>, ArgumentHelper> temp = RegistryHelper.INSTANCE.getAnnotationMap();
		blockMaker = new MakerStruct<Block>(temp)
		{
			@Override
			protected ImmutableSet<Namespace> warp(Field f, Block target)
			{
				Namespace space = Namespace.newSpace(f.getName(), target);
				String ore = null;
				OreDic anno = f.getAnnotation(OreDic.class);
				if (anno != null)
					ore = anno.value();
				space.setOreName(ore);
				return ImmutableSet.of(space);
			}
		};
		itemMaker = new MakerStruct<Item>(temp)
		{
			@Override
			protected ImmutableSet<Namespace> warp(Field f, Item target)
			{
				Namespace space = Namespace.newSpace(f.getName(), target);
				String ore = null;
				OreDic anno = f.getAnnotation(OreDic.class);
				if (anno != null)
					ore = anno.value();
				space.setOreName(ore);
				return ImmutableSet.of(space);
			}
		};
		maker = new MakerStruct<Object>(temp)
		{
			@Override
			protected ImmutableSet<Namespace> warp(Field f, Object target)
			{
				ImmutableSet.Builder<Namespace> builder = ImmutableSet.builder();
				try
				{
					Class<?> clz = target.getClass();
					for (Field field : clz.getDeclaredFields())
					{
						if (Modifier.isStatic(field.getModifiers()))
							continue;
						if (!field.isAccessible())
							field.setAccessible(true);
						Class type = field.getType();
						Namespace space = null;
						if (Block.class.isAssignableFrom(type))
							builder.add(space = Namespace.newSpace(field.getName(), (Block) field.get(target)));
						else if (Item.class.isAssignableFrom(type))
							builder.add(space = Namespace.newSpace(field.getName(), (Item) field.get(target)));
						String ore = null;
						OreDic anno = field.getAnnotation(OreDic.class);
						if (anno != null)
							ore = anno.value();
						space.setOreName(ore);
					}
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				return builder.build();
			}
		};
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			CommonLogger.info("Start to register [".concat(meta.modid).concat("] mod."));
			this.collect(meta);
			RegistryHelper.INSTANCE.end();
		}
	}

	private void collect(ContainerMeta meta)
	{
		for (Field f : meta.getFields())
		{
			ImmutableSet<Namespace> result = this.make(f);
			if (result != null)
				meta.addUnregistered(result);
		}
	}

	private void register(ContainerMeta meta)
	{
		for (ImmutableSet<Namespace> node : meta.getUnregistered())
		{
			String registerName;
			String unlocalizedName;
			for (Namespace namespace : node)
			{
				registerName = NameFormattor.upperTo_(namespace.toString());
				unlocalizedName = NameFormattor._toPoint(registerName);
				namespace.getComponent().setUnlocalizedName(unlocalizedName);
				namespace.getComponent().register(registerName);
				if (namespace.needRegOre())
					namespace.getComponent().registerOre(namespace.getOreName());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void registerClient(ContainerMeta meta)
	{
		for (ImmutableSet<Namespace> node : meta.getUnregistered())
		{
			for (Namespace namespace : node)
				if (meta.getModelHandler() == null || !meta.getModelHandler().handle(namespace.getComponent()))
					namespace.getComponent().registerModel(NameFormattor.upperTo_(namespace.toString()));
			if (HelperMod.DEBUG_MOD)
			{
				FileReference.registerFile(meta.modid);
				LanguageReporter.instance().setLangType(meta.modid, meta.langType());
				for (Namespace namespace : node)
					LanguageReporter.instance().report(namespace.getComponent().getUnlocalizedName());
				try
				{
					LanguageReporter.instance().writeLang();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			this.register(meta);
			if (HelperMod.proxy.isClient())
				registerClient(meta);
			RegistryHelper.INSTANCE.end();
		}
	}

	private ImmutableSet<Namespace> make(Field f)
	{
		Class c = f.getType();
		if (Item.class.isAssignableFrom(c))
			return itemMaker.make(f);
		if (Block.class.isAssignableFrom(c))
			return blockMaker.make(f);
		if (c.isAnnotationPresent(BlockItemStruct.class))
			return maker.make(f);
		return null;
	}
}
