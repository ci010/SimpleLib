package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.event.InteractiveIntegerMissMatchEvent;
import api.simplelib.interactive.process.Process;
import net.simplelib.interactive.process.VarInteger;

import java.util.List;

/**
 * @author ci010
 */
public class InteractiveEntityUpdate extends InteractiveEntity implements IUpdatePlayerListBox
{
	protected List<Process> processes;
	protected List<VarInteger> integers;

	protected InteractiveEntityUpdate(Interactive real, String id, World world, List<ITagSerial> properties)
	{
		super(real, id, world, properties);
	}

//	public InteractiveEntityUpdate(String name, World world)
//	{
//		super(name, world);
//	}

//	@Override
//	public ContainerCommon loadToContainer(ContainerCommon container)
//	{
//		return super.loadToContainer(container).load(ImmutableList.copyOf(integers));
//	}

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
			arr[i] = integers.get(i).get();
		tag.setIntArray("VarInt", arr);
	}
}
