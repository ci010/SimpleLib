package net.simplelib;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.simplelib.abstracts.ArgumentHelper;
import net.simplelib.abstracts.BlockItemStruct;
import net.simplelib.annotation.field.Construct;
import net.simplelib.data.ContainerMeta;
import net.simplelib.data.StructBlock;
import net.simplelib.data.StructItem;
import net.simplelib.network.AbstractMessageHandler;
import net.simplelib.registry.AIRegistry;
import net.simplelib.registry.SitHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class handles most block/item create/register things.
 */
public enum RegistryHelper
{
	INSTANCE;

	private Map<String, ContainerMeta> containerIdx = Maps.newHashMap();

	private Map<Class<? extends Annotation>, ArgumentHelper> annoMap = Maps.newHashMap();

	void track(ContainerMeta meta)
	{
		this.containerIdx.put(meta.modid, meta);
	}

	public void setLang(String modid, String[] lang)
	{
		if (lang == null || lang.length == 0)
			lang = new String[]
					{"zh_CN", "en_US"};
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).lang(true).langType(lang));
		else
			this.containerIdx.get(modid).lang(true).langType(lang);
	}

	public void setModel(String modid)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).model(true));
		else
			this.containerIdx.get(modid).model(true);
	}

	public void registerMod(String modid, Class<?> container)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addField(this.parseContainer(container)));
		else
			this.containerIdx.get(modid).addField(this.parseContainer(container));
	}

	public void registerBlock(String modid, Block block, String name)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addUnregistered(new StructBlock(block), name, false));
		else
			this.containerIdx.get(modid).addUnregistered(new StructBlock(block), name, false);
	}

	public void registerBlock(String modid, Block block, String name, String ore)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addUnregistered(new StructBlock(block), name, ore));
		else
			this.containerIdx.get(modid).addUnregistered(new StructBlock(block), name, ore);
	}

	public void registerItem(String modid, Item item, String name)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addUnregistered(new StructItem(item), name, false));
		else
			this.containerIdx.get(modid).addUnregistered(new StructItem(item), name, false);
	}

	public void registerItem(String modid, Item item, String name, String ore)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addUnregistered(new StructItem(item), name, ore));
		else
			this.containerIdx.get(modid).addUnregistered(new StructItem(item), name, ore);
	}

	public Map<Class<? extends Annotation>, ArgumentHelper> getAnnotationMap()
	{
		return ImmutableMap.copyOf(this.annoMap);
	}

	public Iterator<ContainerMeta> getRegistryInfo()
	{
		return this.containerIdx.values().iterator();
	}

	public Set<Field> parseContainer(Class<?> container)
	{//TODO check permission
		Set<Field> temp = Sets.newHashSet();
		for (Field f : container.getFields())
			if (Modifier.isStatic(f.getModifiers()))
				temp.add(f);
			else
				HelperMod.LOG.info("The field {} in container {} is not static so that it won'registerInit be constructed and registered",
						f.getName(),
						container.getName());
		return temp;
	}

	/**
	 * Make the block sittable
	 *
	 * @param block
	 */
	public void registerSittableBlock(Block block)
	{
		SitHandler.register(block);
	}

	/**
	 * Register new message with its handler class
	 *
	 * @param handlerClass The handler class which binds with message class
	 * @param messageClass The message class
	 * @param <Message>    The type of message
	 */
	public <Message extends IMessage> void registerMessage(Class<? extends AbstractMessageHandler<Message>>
																   handlerClass, Class<Message> messageClass)
	{
		ModNetwork.instance().registerMessage(handlerClass, messageClass);
	}

	/**
	 * Register custom annotation for constructing object.
	 * <p/>
	 * See {@link Construct.Float#value()} with
	 * {@link Construct.FloatHelper#getArguments(Annotation)}
	 *
	 * @param annotation The annotation used to catch constructing arguments.
	 * @param helper     The helper will handle the annotation above.
	 */
	public void registerAnnotation(Class<? extends Annotation> annotation, ArgumentHelper helper)
	{
		if (!annoMap.containsKey(annotation))
			annoMap.put(annotation, helper);
		else
			throw new IllegalArgumentException("The annotation has already been registerd!");
	}

	/**
	 * Register containers without both language files and model files.
	 * <p/>
	 * See {@link RegistryHelper#register(boolean, boolean, Class[])}
	 *
	 * @param containers The item/block/custom containers.
	 */
	public void register(Class<?>... containers)
	{
		register(false, false, containers);
	}

	/**
	 * Register containers. All the static fields with type assigning from
	 * {@link Item}/ {@link Block}/{@link BlockItemStruct} in these containers
	 * will be registered.
	 *
	 * @param ifGenerateLang  If your mod need to generate language files
	 * @param ifGenerateModel If your mod need to generate model files
	 * @param containers      The item/block/custom containers.
	 */
	public void register(boolean ifGenerateLang, boolean ifGenerateModel, Class<?>... containers)
	{
		String modid = Loader.instance().activeModContainer().getModId();
		ContainerMeta meta = new ContainerMeta(modid).lang(ifGenerateLang).model(ifGenerateModel);
		for (Class<?> container : containers)
			meta.addField(parseContainer(container));
		this.track(meta);
	}

	/**
	 * Check if all the fields are created.
	 */
	public void check()
	{
		boolean isClear = true;
		for (ContainerMeta meta : containerIdx.values())
			for (Field f : meta.getFields())
				try
				{
					if (f.get(null) == null)
					{
						HelperMod.LOG.fatal("field" + f.getName() + "is NULL!");
						isClear = false;
					}
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}

		if (isClear)
			HelperMod.LOG.info("Containers are all fine");
	}

	/**
	 * Remove the ai in a entity living class
	 *
	 * @param living The class of the entity living
	 * @param ai     The ai you want to remove
	 */
	public void removeAI(Class<? extends EntityLiving> living, Class<? extends EntityAIBase>... ai)
	{
		AIRegistry.removeAI(living, ai);
	}

	public void close()
	{
		if (Loader.instance().getLoaderState() == LoaderState.AVAILABLE)
		{
			this.containerIdx = null;
			this.annoMap = null;
		}
	}
}
