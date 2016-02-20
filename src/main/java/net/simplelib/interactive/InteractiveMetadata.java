package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.HelperMod;
import net.simplelib.common.nbt.ITagSerial;

import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class InteractiveMetadata// implements GuiHandler.ContainerProvider
{
	private static Map<String, InteractiveMetadata> registerMap = Maps.newHashMap();

	public static void register(String id, InteractiveMetadata metadata)
	{
		if (registerMap.containsKey(id))
		{
			HelperMod.LOG.fatal("The duplicated id!");//TODO finish this
			throw new IllegalArgumentException("The duplicated id!");
		}
		registerMap.put(id, metadata);
	}

	public static InteractiveMetadata getMetaData(String id)
	{
		return registerMap.get(id);
	}

//	public static void openGui(EntityPlayer player, BlockPos pos, Interactive.Gui interactWith)
//	{
//		player.openGui(HelperMod.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
//	}

	private static Map<Class, Class<? extends IProperty>> metaDataConstructMap;

	public static void register(Class<? extends Interactive> interf, Class<? extends IProperty> meta)
	{
		metaDataConstructMap.put(interf, meta);
	}

	private String id;
	private Interactive provider;
	private List<IProperty> metas;

	public InteractiveMetadata(Interactive provider)
	{
		this.id = provider.getId();

		this.provider = provider;

		Class<?>[] interfaces = provider.getClass().getInterfaces();

		ImmutableList.Builder<IProperty> builder = ImmutableList.builder();

		for (Class<?> anInterface : interfaces)
		{
			Class<? extends IProperty> aClass = metaDataConstructMap.get(anInterface);
			if (aClass != null)
			{
				try
				{
					IProperty meta = aClass.newInstance();
					if (!meta.shouldApply(this.provider))
						continue;
					meta.init(provider);
					builder.add(meta);
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
		this.metas = builder.build();
	}

	public void interactWith(EntityPlayer player, BlockPos pos)
	{
		this.provider.interactWith(player, pos);
	}

	public InteractiveEntity createEntity(World world)
	{
		ImmutableList.Builder<ITagSerial> builder = ImmutableList.builder();
		for (IProperty meta : metas)
		{
			ITagSerial data = meta.buildProperty();
			if (data == null)
				continue;
			builder.add(data);
		}
		return new InteractiveEntity(this.provider, this.id, world, builder.build());
	}
}
