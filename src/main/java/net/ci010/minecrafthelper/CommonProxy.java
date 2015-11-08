package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.abstracts.ASMDataParser;
import net.ci010.minecrafthelper.annotation.Message;
import net.ci010.minecrafthelper.annotation.ModEntity;
import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonProxy
{
	//TODO merger parser and cache into a class
	public void loadASMDataTable(ASMDataTable table)
	{
		Set<?> s = new HashSet<ASMData>();
		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				RegistryHelper.INSTANCE.putContainer(this.getId(data), this.getClass(data));
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.BlockItemContainer"));

		new ASMDataParser()
		{
			@SuppressWarnings("unchecked")
			@Override
			protected void parse(ASMData data)
			{
				Class<IMessage> msg = (Class<IMessage>) this.getClass(data);
				Class<AbstractMessageHandler<IMessage>> handler = (Class<AbstractMessageHandler<IMessage>>) this.getAnnotation(data,
						Message.class).value();
				RegistryHelper.INSTANCE.registerMessage(handler, msg);
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.Message"));

		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				Object handler = null;
				try
				{
					handler = this.getClass(data).newInstance();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				MinecraftForge.EVENT_BUS.register(handler);
				FMLCommonHandler.instance().bus().register(handler);
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.Handler"));

		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				//TODO check this
				Class<?> clz = this.getClass(data);
				if (Item.class.isAssignableFrom(clz))
				{
					try
					{
						ComponentsRepository.put(clz.getName(), (Item) clz.newInstance());
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
				else if (Block.class.isAssignableFrom(clz))
				{
					try
					{
						ComponentsRepository.put(clz.getName(), (Block) clz.newInstance());
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
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.StaticComponent"));

		new ASMDataParser()
		{
			@Override
			@SuppressWarnings("unchecked")
			protected void parse(ASMData data)
			{
				CommandCache.instance().addCommand((Class<? extends CommandBase>) this.getClass(data));
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.Command"));


		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				String modid = this.getId(data);
				ModEntity anno = this.getAnnotation(data, ModEntity.class);
				String name = anno.name();
				if (name.equals(""))
					name = data.getClassName();
				int id = anno.id();
				if (id == -1)
					id = EntityId.next(modid);
				Class<? extends Entity> clz = (Class<? extends Entity>) this.getClass(data);
				EntityRegistryCache.instance().add(new EntityRegistryCache.EntityRegisterInfo(clz, Loader.instance()
						.getIndexedModList().get(modid).getMod(), name, id,
						anno
								.trackingRange(), anno.updateFrequency(), anno.sendsVelocityUpdates()));
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.ModEntity"));
	}

	static class EntityId
	{
		static Map<String, Integer> m = Maps.newHashMap();

		static int next(String modid)
		{
			if (!m.containsKey(modid))
				m.put(modid, -1);
			int next = m.get(modid) + 1;
			m.put(modid, next);
			return next;
		}
	}

	public boolean isClient() {return false;}

	public boolean isClientSide() {return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;}
}
