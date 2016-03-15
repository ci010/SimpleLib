package net.simplelib.common.nbt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.*;
import api.simplelib.utils.GenericUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ci010
 */
public class NBTBasement implements Deserializer<Object>, Serializer<Object>
{
	private static NBTBasement instance;

	public static NBTBasement instance()
	{
		if (instance == null)
			instance = new NBTBasement();
		return instance;
	}

	public ImmutableList<FullSerializer> getList()
	{
		return built_in;
	}

	@Override
	public Object deserialize(NBTBase base)
	{
		if (base == null)
			return null;
		if (base.getId() == 10)
			throw new IllegalArgumentException("Cannot directly deserialize an NBTTagCompound!");
		if (base.getId() == 9)
			throw new IllegalArgumentException("Cannot directly deserialize an NBTTagList!");
		return GenericUtil.<Deserializer<Object>>cast(built_in.get(base.getId())).deserialize(base);
	}

	@Override
	public NBTBase serialize(Object data)
	{
		FullSerializer serializer = built_in_map.get(data.getClass());
		if (serializer != null)
			return GenericUtil.<Serializer<Object>>cast(serializer).serialize(data);
		return null;
	}

	interface FullSerializer<T> extends Deserializer<T>, Serializer<T> {}

	private ImmutableList<FullSerializer> built_in;
	private ImmutableMap<Class, FullSerializer> built_in_map;

	private NBTBasement()
	{
		ImmutableList.Builder<FullSerializer> listBuilder = ImmutableList.builder();
		ImmutableMap.Builder<Class, FullSerializer> mapBuilder = ImmutableMap.builder();
		listBuilder.add(new FullSerializer<Void>()
		{
			@Override
			public NBTBase serialize(Void data)
			{
				return new NBTTagEnd();
			}

			@Override
			public Void deserialize(NBTBase base)
			{
				return null;
			}
		}).add(new FullSerializer<Byte>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Byte data)
			{
				return new NBTTagByte(data);
			}

			@Override
			public Byte deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getByte();
			}
		}).add(new FullSerializer<Short>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Short data)
			{
				return new NBTTagShort(data);
			}

			@Override
			public Short deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getShort();
			}
		}).add(new FullSerializer<Integer>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Integer data)
			{
				return new NBTTagInt(data);
			}

			@Override
			public Integer deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getInt();
			}
		}).add(new FullSerializer<Long>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Long data)
			{
				return new NBTTagLong(data);
			}

			@Override
			public Long deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getLong();
			}
		}).add(new FullSerializer<Float>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Float data)
			{
				return new NBTTagFloat(data);
			}

			@Override
			public Float deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getFloat();
			}

		}).add(new FullSerializer<Double>()
		{
			@Override
			public NBTBase.NBTPrimitive serialize(Double data)
			{
				return new NBTTagDouble(data);
			}

			@Override
			public Double deserialize(NBTBase base)
			{
				return ((NBTBase.NBTPrimitive) base).getDouble();
			}
		}).add(new FullSerializer<byte[]>()
		{
			@Override
			public NBTTagByteArray serialize(byte[] data)
			{
				return new NBTTagByteArray(data);
			}

			@Override
			public byte[] deserialize(NBTBase base)
			{
				return ((NBTTagByteArray) base).getByteArray();
			}
		}).add(new FullSerializer<String>()
		{
			@Override
			public NBTTagString serialize(String data)
			{
				return new NBTTagString(data);
			}

			@Override
			public String deserialize(NBTBase base)
			{
				return ((NBTTagString) base).getString();
			}
		}).add(new FullSerializer<List>()
		{
			@Override
			public NBTBase serialize(List data)
			{
				return null;
			}

			@Override
			public List deserialize(NBTBase base)
			{
				return null;
			}
		}).add(new FullSerializer<Object>()
		{
			@Override
			public Object deserialize(NBTBase base)
			{
				return null;
			}

			@Override
			public NBTTagCompound serialize(Object data)
			{
				NBTTagCompound tag = new NBTTagCompound();
				Class<?> clz = data.getClass();
				for (Field field : clz.getDeclaredFields())
				{
					if (field.isAccessible())
						try
						{
							Object f = field.get(data);
							tag.setTag(field.getName(), null);
						}
						catch (IllegalAccessException e)
						{
							e.printStackTrace();
						}
				}
				return tag;
			}
		}).add(new FullSerializer<int[]>()
		{
			@Override
			public NBTTagIntArray serialize(int[] data)
			{
				return new NBTTagIntArray(data);
			}

			@Override
			public int[] deserialize(NBTBase base)
			{
				return ((NBTTagIntArray) base).getIntArray();
			}
		});
		built_in = listBuilder.build();
		for (FullSerializer fullSerializer : built_in)
			mapBuilder.put(GenericUtil.getInterfaceGenericTypeTo(fullSerializer), fullSerializer);
		built_in_map = mapBuilder.build();
	}


}
