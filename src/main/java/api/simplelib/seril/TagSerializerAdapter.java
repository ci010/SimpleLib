package api.simplelib.seril;

import com.google.gson.GsonBuilder;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public class TagSerializerAdapter<T> implements INBTSerializer<T>
{
	private IJsonSerializer<T> jsonSerializer;

	@Override
	public T deserialize(NBTTagCompound tag)
	{
		return null;
	}

	@Override
	public NBTTagCompound serialize(T data)
	{
		try
		{
			return JsonToNBT.getTagFromJson(new GsonBuilder().registerTypeAdapter(data.getClass(), jsonSerializer).create()
					.toJson(data));
		}
		catch (NBTException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
