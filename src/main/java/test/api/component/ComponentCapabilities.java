package test.api.component;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import test.api.inventory.InventoryManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class ComponentCapabilities
{
	@CapabilityInject(Pipeline.class)
	public static final Capability<Pipeline> pipelineCapability = null;
	@CapabilityInject(InventoryManager.class)
	public static final Capability<InventoryManager> inventoryCapability = null;

	static
	{
		CapabilityManager.INSTANCE.register(Pipeline.class, new Capability.IStorage<Pipeline>()
		{
			@Override
			public NBTBase writeNBT(Capability<Pipeline> capability, Pipeline instance, EnumFacing side)
			{
				NBTTagCompound tag = new NBTTagCompound();
				ListIterator<Work> iterator = instance.iterator();
				while (iterator.hasNext())
					iterator.next().writeToNBT(tag);
				return tag;
			}

			@Override
			public void readNBT(Capability<Pipeline> capability, Pipeline instance, EnumFacing side, NBTBase nbt)
			{
				if (nbt instanceof NBTTagCompound)
				{
					ListIterator<Work> iterator = instance.iterator();
					while (iterator.hasNext())
						iterator.next().readFromNBT((NBTTagCompound) nbt);
				}
			}
		}, new Callable<Pipeline>()
		{
			@Override
			public Pipeline call() throws Exception
			{
				return new Pipeline()
				{
					@Override
					public ListIterator<Work> iterator()
					{
						return works.listIterator(0);
					}

					private LinkedList<Work> works = new LinkedList<Work>();

					@Override
					public int addLast(Work work)
					{
						works.addLast(work);
						return works.size() - 1;
					}

					@Override
					public int addFirst(Work work)
					{
						works.addFirst(work);
						return 0;
					}

					@Override
					public int addAfter(Work work, int i)
					{
						works.add(i + 1, work);
						return i + 1;
					}

					@Override
					public int addBefore(Work work, int i)
					{
						works.add(i, work);
						return i;
					}

					@Override
					public void removeWork(int id)
					{
						works.remove(id);
					}

					@Override
					public int size()
					{
						return works.size();
					}
				};
			}
		});
	}
}
