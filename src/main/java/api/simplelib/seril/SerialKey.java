package api.simplelib.seril;

import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public abstract class SerialKey<T>
{
	private ResourceLocation location;
	private Class<T> type;

	private SerialKey(ResourceLocation location, Class<T> type)
	{
		this.location = location;
		this.type = type;
	}

	public ResourceLocation location()
	{
		return location;
	}

	public Class<T> type()
	{
		return type;
	}

	public static class Json<T> extends SerialKey<T>
	{
		private IJsonSerializer<T> serializer;

		Json(ResourceLocation location, Class<T> type, IJsonSerializer<T> serializer)
		{
			super(location, type);
			this.serializer = serializer;
		}

		public IJsonSerializer<T> serializer()
		{
			return serializer;
		}
	}

	public static class NBT<T> extends SerialKey<T>
	{
		private INBTSerializer<T> serializer;

		NBT(ResourceLocation location, Class<T> type, INBTSerializer<T> serializer)
		{
			super(location, type);
			this.serializer = serializer;
		}

		public INBTSerializer<T> serializer()
		{
			return serializer;
		}
	}
}
