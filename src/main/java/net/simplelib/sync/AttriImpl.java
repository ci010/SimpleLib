package net.simplelib.sync;

import api.simplelib.sync.AttributeView;
import api.simplelib.sync.CapabilityProvidersSerializer;
import api.simplelib.sync.UpdateMode;
import api.simplelib.sync.VarSync;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;

/**
 * @author ci010
 */
public class AttriImpl implements AttributeView
{
	protected ImmutableList<VarSync<?>> list, constantly, lazy;
	protected ImmutableBiMap<String, VarSync<?>> map;
	protected final NBTTagCompound location;

	public AttriImpl(ICapabilityProvider src, Map<String, VarSync<?>> constantly, Map<String, VarSync<?>> lazy)
	{
		this.location = CapabilityProvidersSerializer.of(src);
		this.map = ImmutableBiMap.<String, VarSync<?>>builder().putAll(constantly).putAll(lazy).build();
		this.constantly = ImmutableList.copyOf(constantly.values());
		this.lazy = ImmutableList.copyOf(lazy.values());
		this.list = ImmutableList.<VarSync<?>>builder().addAll(this.lazy).addAll(this.constantly).build();
	}

	@Override
	public VarSync getById(int id)
	{
		return list.get(id);
	}

	@Override
	public VarSync getByName(@Nonnull String name)
	{
		return map.get(name);
	}

	@Nonnull
	@Override
	public Collection<? extends VarSync<?>> getAll()
	{
		return list;
	}

	@Override
	public VarSync<?>[] toArray()
	{
		return new VarSync[0];
	}

	@Override
	public int size()
	{
		return list.size();
	}

	@Nonnull
	@Override
	public Collection<String> allPresent()
	{
		return map.keySet();
	}

	@Nonnull
	@Override
	public ImmutableList<VarSync<?>> getVarsByMode(@Nonnull UpdateMode mode)
	{
		return mode == UpdateMode.CONSTANTLY ? constantly : lazy;
	}

	public static class Entity extends AttriImpl implements ChangeListener<Object>
	{
		public Entity(ICapabilityProvider src, Map<String, VarSync<?>> constantly, Map<String, VarSync<?>> lazy)
		{
			super(src, constantly, lazy);
		}

		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
		{

		}
	}
//	public static class SyncMessage extends NBTClientMessageHandler
//	{
//		public SyncMessage()
//		{
//			super();
//		}
//
//		SyncMessage(NBTTagCompound data)
//		{
//			this.delegate.set(data);
//		}
//
//		@Override
//		public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
//		{
//			NBTTagCompound real = data.getCompoundTag("data");
//			NBTTagCompound loc = data.getCompoundTag("loc");
//			ICapabilityProvider of = CapabilityProvidersSerializer.of(loc);
//			if (of == null)
//				throw new IllegalArgumentException();
//			AttributeView capability = of.getCapability(CAPABILITY, null);
//			for(String s : real.getKeySet())
//				capability.getByName(s).readFromNBT(real);
//			return null;
//		}
//	}
}
