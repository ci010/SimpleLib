package net.simplelib.interactive.process;

import api.simplelib.Var;
import api.simplelib.VarSync;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.meta.ModInteractiveMeta;
import api.simplelib.interactive.process.Process;
import api.simplelib.interactive.process.ProcessPipeline;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.simplelib.common.VarSyncPrimitive;
import net.simplelib.common.nbt.ITagSerial;
import api.simplelib.interactive.meta.InteractiveProperty;
import net.simplelib.interactive.inventory.InventoryManager;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.*;
import java.util.List;

/**
 * @author ci010
 */
@ModInteractiveMeta
public class ProcessHandler implements ProcessPipeline.Handler, InteractiveProperty<ProcessPipeline.Data, Void, ProcessPipeline>
{
	private List<Process> cache;
	private List<Pair<SlotInfo, Var<ItemStack>>> stackCache;
	private List<VarSync> data;

	protected ProcessPipeline pipeline;

	private ProcessPipeline.Factory factory = new ProcessPipeline.Factory()
	{
		@Override
		public Var<ItemStack> newStack(SlotInfo info)
		{
			if (!(info instanceof InventoryManager.Info))
			{
				throw new IllegalArgumentException("Please use the inventory's");//TODO log
			}
			if (stackCache == null)
				stackCache = Lists.newArrayList();
			Var<ItemStack> holder = new VarItemHolder();
			stackCache.add(Pair.of(info, holder));
			return holder;
		}

		@Override
		public Var<Integer> newInteger(String name, int i)
		{
			VarSyncPrimitive<Integer> var = new VarSyncPrimitive<Integer>(name, i);
			data.add(var);
			return var;
		}

		@Override
		public Var<Float> newFloat(String name, float f)
		{
			VarSyncPrimitive<Float> var = new VarSyncPrimitive<Float>(name, f);
			data.add(var);
			return var;
		}

		@Override
		public Var<Short> newShort(String name, short l)
		{
			VarSyncPrimitive<Short> var = new VarSyncPrimitive<Short>(name, l);
			data.add(var);
			return var;
		}

		@Override
		public Var<Byte> newByte(String name, byte b)
		{
			VarSyncPrimitive<Byte> var = new VarSyncPrimitive<Byte>(name, b);
			data.add(var);
			return var;
		}

		@Override
		public Var<String> newString(String name, String s)
		{
			VarSyncPrimitive<String> var = new VarSyncPrimitive<String>(name, s);
			data.add(var);
			return var;
		}
	};

	/**
	 * Add the actual process to this interactWith component.
	 *
	 * @param process The process will be added.
	 * @return The Process handler.
	 */
	public ProcessHandler addProcess(Process process)
	{
		if (cache == null)
			cache = Lists.newArrayList();
		cache.add(process);
		return this;
	}

	@Override
	public boolean init(Interactive interactive)
	{
		if (interactive instanceof ProcessPipeline)
		{
			this.pipeline = (ProcessPipeline) interactive;
			this.data = Lists.newArrayList();
			this.pipeline.provideProcess(this, factory);
			if (this.cache == null)
				return false;
			this.clear();
			return true;
		}
		return false;
	}

	private void clear()
	{
		this.cache.clear();
		this.stackCache.clear();
		this.data.clear();
	}

	@Override
	public Entity buildProperty()
	{
		pipeline.provideProcess(this, factory);
		Entity entity = new Entity(this.cache, this.data, this.stackCache);
		this.clear();
		return entity;
	}

	@Override
	public Void getMeta()
	{
		return null;
	}

	@Override
	public Class<ProcessPipeline> getHook()
	{
		return ProcessPipeline.class;
	}

	public class Entity implements ProcessPipeline.Data, ITickable
	{
		private List<Process> processes;
		private List<VarSync> vars;
		private List<Pair<SlotInfo, Var<ItemStack>>> stack;

		public Entity(List<Process> processes, List<VarSync> vars, List<Pair<SlotInfo, Var<ItemStack>>> stack)
		{
			this.processes = processes;
			this.vars = vars;
			this.stack = stack;
		}

		public List<Pair<SlotInfo, Var<ItemStack>>> getStack()
		{
			return stack;
		}

		public List<VarSync> getVars()
		{
			return vars;
		}

		@Override
		public void readFromNBT(NBTTagCompound tag)
		{
			for (ITagSerial var : vars)
				var.readFromNBT(tag);
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			for (ITagSerial var : vars)
				var.writeToNBT(tag);
		}

		@Override
		public void update()
		{
			for (Process process : processes)
				process.preUpdate();
			for (Process process : processes)
				process.update();
			for (Process process : processes)
				process.postUpdate();
		}
	}

}
