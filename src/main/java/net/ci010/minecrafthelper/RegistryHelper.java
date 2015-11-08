package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.abstracts.ArgumentHelper;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.ci010.minecrafthelper.annotation.Construct;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static net.ci010.minecrafthelper.HelperMod.LOG;

/**
 * This class handles most block/item create/register things.
 */
public enum RegistryHelper
{
	INSTANCE;

	private AIRemove aiRemove = new AIRemove();

	private BlockItemRegistry registry = new BlockItemRegistry();

	private Map<String, ContainerMeta> containerIdx = Maps.newHashMap();

	void track(ContainerMeta meta)
	{
		this.containerIdx.put(meta.modid, meta);
	}

	void setLang(String modid, boolean ifGenerateLang, String... lang)
	{
		if (lang == null)
			lang = new String[]
					{"zh_CN", "en_US"};
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).lang(ifGenerateLang).langType(lang));
		else
			this.containerIdx.get(modid).lang(ifGenerateLang).langType(lang);
	}

	void setModel(String modid, boolean ifGenerateModel)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).model(ifGenerateModel));
		else
			this.containerIdx.get(modid).model(ifGenerateModel);
	}

	public void putContainer(String modid, Class<?> container)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addField(this.parseContainer(container)));
		else
			this.containerIdx.get(modid).addField(this.parseContainer(container));
	}

	public Iterator<ContainerMeta> getRegistryInfo()
	{
		return this.containerIdx.values().iterator();
	}

	Set<Field> parseContainer(Class<?> container)
	{
		Set<Field> temp = Sets.newHashSet();
		for (Field f : container.getFields())
			if (Modifier.isStatic(f.getModifiers()))
				temp.add(f);
			else
				LOG.info("The field {} in container {} is not static so that it won't be constructed and registered",
						f.getName(),
						container.getName());
		return temp;
	}

	/**
	 * Register a new Command
	 *
	 * @param cmd
	 */
	public void registerCommand(CommandBase cmd)
	{
		CommandCache.instance().add(cmd);
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
		if (!registry.map.containsKey(annotation))
			registry.map.put(annotation, helper);
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
						LOG.fatal("field" + f.getName() + "is NULL!");
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
			LOG.info("Containers are all fine");
	}

	/**
	 * Remove the ai in a entity living class
	 *
	 * @param living The class of the entity living
	 * @param ai     The ai you want to remove
	 */
	public void removeAI(Class<? extends EntityLiving> living, Class<? extends EntityAIBase>... ai)
	{
		this.aiRemove.removeAI(living, ai);
	}

	void close()
	{
		if (Loader.instance().getLoaderState() == LoaderState.AVAILABLE)
		{
			this.containerIdx = null;
			this.registry = null;
			this.aiRemove = null;
		}
	}

	AIRemove getAiRemove()
	{
		return this.aiRemove;
	}
}
