package net.simplelib.interactive;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.simplelib.data.VarInteger;
import net.simplelib.data.VarSync;
import net.simplelib.event.InteractiveIntegerMissMatchEvent;

import java.util.List;

/**
 * @author ci010
 */
public class InteractiveEntityUpdate extends InteractiveEntity implements IUpdatePlayerListBox
{
	protected List<Process> processes;
	protected List<VarInteger> integers;

	protected InteractiveEntityUpdate(String name, World world)
	{
		super(name, world);
	}

	@Override
	public ContainerCommon loadToContainer(ContainerCommon container)
	{
		return super.loadToContainer(container).load(ImmutableList.copyOf(integers));
	}

	protected InteractiveEntity loadProcess(List<Process> processes, List<VarInteger> intes, List<VarSync> nbts)
	{
		this.sync = ImmutableList.copyOf(nbts);
		this.processes = processes;
		this.integers = intes;
		return this;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			for (Process proces : processes)
				proces.preUpdate();
			for (Process proces : processes)
				if (proces.shouldUpdate())
					proces.update();
			for (Process proces : processes)
				proces.postUpdate();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		int[] ints = tag.getIntArray("VarInt");
		if (ints.length != this.integers.size())
			MinecraftForge.EVENT_BUS.post(new InteractiveIntegerMissMatchEvent(this.id, ints, integers.toArray(new
					VarInteger[integers.size()])));
		else
			for (int i = 0; i < ints.length; ++i)
				integers.get(i).setData(ints[i]);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		int[] arr = new int[this.integers.size()];
		for (int i = 0; i < this.integers.size(); ++i)
			arr[i] = integers.get(i).getData();
		tag.setIntArray("VarInt", arr);
	}
}
