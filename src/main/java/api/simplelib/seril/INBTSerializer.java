package api.simplelib.seril;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Bean style serializer. This is a external serializer for a type.
 *
 * @author ci010
 * @see ICompoundSerializable
 */
public interface INBTSerializer<T> extends NBTSerializer<T>, NBTDeserializer<T>
{
	class Builder
	{
		public static <T> INBTSerializer<T> newSerializer(final NBTSerializer<T> serializer, final NBTDeserializer<T> deserializer)
		{
			return new INBTSerializer<T>()
			{
				@Override
				public T deserialize(NBTTagCompound tag)
				{
					return deserializer.deserialize(tag);
				}

				@Override
				public NBTTagCompound serialize(T data)
				{
					return serializer.serialize(data);
				}
			};
		}

		public static <T> INBTSerializer<T> newSerializer(final String name,
														  final NBTSerializer.Base<T> serializer,
														  final NBTDeserializer.Base<T>
																  deserializer)
		{
			return new INBTSerializer<T>()
			{
				@Override
				public T deserialize(NBTTagCompound tag)
				{
					return deserializer.deserialize(tag.getTag(name));
				}

				@Override
				public NBTTagCompound serialize(T data)
				{
					return NBTTagBuilder.create().addTag(name, serializer.serialize(data)).build();
				}
			};
		}

		public static <T> INBTSerializer<T> newTempSerializer(final NBTSerializer.Base<T> serializer,
															  final NBTDeserializer.Base<T>
																	  deserializer)
		{
			return new INBTSerializer<T>()
			{
				String id = UUID.randomUUID().toString();

				@Override
				public T deserialize(NBTTagCompound tag)
				{
					return deserializer.deserialize(tag.getTag(id));
				}

				@Override
				public NBTTagCompound serialize(T data)
				{
					return NBTTagBuilder.create().addTag(id, serializer.serialize(data)).build();
				}
			};
		}
	}

	interface Base<T> extends NBTSerializer.Base<T>, NBTDeserializer.Base<T>
	{}
}
