package test.api.component.item.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class CapabilityItem
{
	static
	{
		CapabilityManager.INSTANCE.register(Nameable.class,
				new Capability.IStorage<Nameable>()
				{
					@Override
					public NBTBase writeNBT(Capability<Nameable> capability, Nameable instance, EnumFacing side)
					{
						return new NBTTagString(instance.getCustomName());
					}

					@Override
					public void readNBT(Capability<Nameable> capability, Nameable instance, EnumFacing side, NBTBase nbt)
					{
						if (nbt instanceof NBTTagString)
							instance.setCustomName(((NBTTagString) nbt).getString());
					}
				},
				new Callable<Nameable>()
				{
					@Override
					public Nameable call() throws Exception
					{
						return new Nameable()
						{
							private String name;

							@Override
							public String getCustomName()
							{
								return name;
							}

							@Override
							public void setCustomName(String name)
							{
								this.name = name;
							}
						};
					}
				});
		CapabilityManager.INSTANCE.register(CustomInfo.class,
				new Capability.IStorage<CustomInfo>()
				{
					@Override
					public NBTBase writeNBT(Capability<CustomInfo> capability, CustomInfo instance, EnumFacing side)
					{
						NBTTagList list = new NBTTagList();
						for (String s : instance.getCustomInfo())
							list.appendTag(new NBTTagString(s));
						return list;
					}

					@Override
					public void readNBT(Capability<CustomInfo> capability, CustomInfo instance, EnumFacing side, NBTBase nbt)
					{
						if (nbt instanceof NBTTagList)
							for (int i = 0; i < ((NBTTagList) nbt).tagCount(); i++)
								instance.getCustomInfo().add(((NBTTagString) ((NBTTagList) nbt).get(i)).getString());
					}
				},
				new Callable<CustomInfo>()
				{
					@Override
					public CustomInfo call() throws Exception
					{
						return new CustomInfo()
						{
							private List<String> str = new ArrayList<String>();

							@Override
							public List<String> getCustomInfo()
							{
								return str;
							}
						};
					}
				});
	}
}
