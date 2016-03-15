package net.simplelib.common.nbt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import api.simplelib.utils.GenericUtil;
import api.simplelib.utils.NBTTagBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class NBTSerial
{
	private Map<Class, Deserializer> deserializerMap;
	private Map<Class, Serializer> serializerMap;

	public NBTTagCompound toNBTCompound(Object obj)
	{
		NBTBase base = this.toNBTBase(obj);
		NBTTagBuilder builder = NBTTagBuilder.newBuilder();
		if (base instanceof NBTTagCompound)
			return (NBTTagCompound) base;
		else
			return builder.addTag("obj", base).build();
	}

	public NBTTagList toNBTList(List list)
	{
		NBTTagList nbtTagList = new NBTTagList();
		for (Object o : list)
			nbtTagList.appendTag(toNBTBase(o));
		return nbtTagList;
	}

	public List fromNBTList(NBTTagList list)
	{
		return null;
	}

	private NBTBase toNBTBase(Object obj)
	{
		Class type = obj.getClass();
		NBTBase base = NBTBasement.instance().serialize(obj);
		if (base != null)
			return base;
		else
		{
			NBTTagBuilder builder = NBTTagBuilder.newBuilder();
			for (Field field : type.getDeclaredFields())
				if (field.isAccessible())
					try
					{
						Object fieldObj = field.get(obj);
						if (fieldObj != null)
							builder.addTag(field.getName(), toNBTBase(obj));
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
			return builder.build();
		}
	}

	public <T> T fromNBT(NBTTagCompound tag, Class<T> type)
	{
//		if (deserializerMap.containsKey(type))
//			return GenericUtil.<Deserializer<T>>cast(deserializerMap.get(type)).deserialize(tag, type);
//		else
//		{
//
//		}
		return null;
	}

	public <T> Deserializer<T> getDeserializer(Class<T> clz)
	{
		if (deserializerMap.containsKey(clz))
			return GenericUtil.cast(deserializerMap.get(clz));
		return null;
	}

	public <T> Serializer<T> getSerializer(Class<T> clz)
	{
		if (serializerMap.containsKey(clz))
			return GenericUtil.cast(serializerMap.get(clz));
		return null;
	}

	public <T> T fromNBTBase(NBTBase base)
	{
//		base.getId();
//		return GenericUtil.<Deserializer<T>>cast(deserializerMap.get(clz)).deserialize(base, clz);
		return null;
	}

	public <T> T fromNBT(NBTTagCompound tag, Class<T> type, Deserializer.Tag<T> deserializer)
	{
		deserializer.deserialize(tag, type);
		return null;
	}

	public NBTSerial with(Deserializer deserializer)
	{
		if (deserializerMap == null)
			deserializerMap = Maps.newHashMap();
		deserializerMap.put(GenericUtil.getGenericTypeTo(deserializer), deserializer);
		return this;
	}

	public NBTSerial with(Serializer serializer)
	{
		serializerMap.put(GenericUtil.getGenericTypeTo(serializer), serializer);
		return this;
	}


	public NBTSerial()
	{
		serializerMap = Maps.newHashMap();
		ImmutableList<NBTBasement.FullSerializer> list = NBTBasement.instance().getList();
		for (NBTBasement.FullSerializer full : list)
		{
//			Serializer serializer = full;
//			Deserializer deserializer = full;
//			this.with(serializer).with(deserializer);
		}
	}
}
