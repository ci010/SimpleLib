package net.simplelib.interactive.process;

import api.simplelib.Context;
import api.simplelib.Var;
import api.simplelib.VarNotify;
import api.simplelib.VarSync;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.meta.InteractiveProperty;
import api.simplelib.interactive.meta.ModInteractiveMeta;
import api.simplelib.interactive.process.Process;
import api.simplelib.interactive.process.ProcessPipeline;
import api.simplelib.utils.GenericUtil;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.simplelib.common.VarSyncPrimitive;
import net.simplelib.interactive.inventory.InventoryManager;
import org.apache.commons.lang3.tuple.Pair;
import api.simplelib.utils.ITagSerializable;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
@ModInteractiveMeta
public class ProcessHandler implements InteractiveProperty,
									   Callable<ProcessPipeline.Data>
{
	private List<Process> cache = Lists.newArrayList();
	private List<Pair<SlotInfo, Var<ItemStack>>> stackCache = Lists.newArrayList();
	private List<VarSync> varCache = Lists.newArrayList();

	private class WorkerProcess implements Worker, ProcessPipeline.Handler
	{
		private ProcessPipeline pipeline;
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
			public <T extends Enum<T>> Var<T> newState(T state, VarNotify.Callback<T> callback)
			{
				VarSync<T> var = new VarSync<T>()
				{
					@Override
					public void readFromNBT(NBTTagCompound tag)
					{
						this.load(Enum.valueOf(this.get().getDeclaringClass(), tag.getString("enum-name")));
					}

					@Override
					public void writeToNBT(NBTTagCompound tag)
					{
						tag.setString("enum-name", this.get().name());
					}
				};
				var.add(callback);
				varCache.add(var);
				return var;
			}

			@Override
			public Var<Integer> newInteger(String name, int i)
			{
				VarSyncPrimitive<Integer> var = new VarSyncPrimitive<Integer>(name, i);
				varCache.add(var);
				return var;
			}

			@Override
			public Var<Float> newFloat(String name, float f)
			{
				VarSyncPrimitive<Float> var = new VarSyncPrimitive<Float>(name, f);
				varCache.add(var);
				return var;
			}

			@Override
			public Var<Short> newShort(String name, short l)
			{
				VarSyncPrimitive<Short> var = new VarSyncPrimitive<Short>(name, l);
				varCache.add(var);
				return var;
			}

			@Override
			public Var<Byte> newByte(String name, byte b)
			{
				VarSyncPrimitive<Byte> var = new VarSyncPrimitive<Byte>(name, b);
				varCache.add(var);
				return var;
			}

			@Override
			public Var<String> newString(String name, String s)
			{
				VarSyncPrimitive<String> var = new VarSyncPrimitive<String>(name, s);
				varCache.add(var);
				return var;
			}
		};


		@Override
		public boolean init(Interactive interactive)
		{
			if (interactive instanceof ProcessPipeline)
			{
				this.pipeline = (ProcessPipeline) interactive;
				this.pipeline.provideProcess(this, factory);
				if (cache.isEmpty())
					return false;
				clear();
				return true;
			}
			return false;
		}

		@Override
		public Entity buildProperty(Context context)
		{
			pipeline.provideProcess(this, factory);
			Entity entity = new Entity(cache, varCache, stackCache, context);
			clear();
			return entity;
		}

		public ProcessPipeline.Handler addProcess(Process process)
		{
			if (cache == null)
				cache = Lists.newArrayList();
			cache.add(process);
			return this;
		}
	}

	@Override
	public Worker newWorker()
	{
		return new WorkerProcess();
	}

	@Override
	public void build()
	{
		CapabilityManager.INSTANCE.register(ProcessPipeline.Data.class, new Capability.IStorage<ProcessPipeline.Data>()
		{
			@Override
			public NBTBase writeNBT(Capability<ProcessPipeline.Data> capability, ProcessPipeline.Data instance, EnumFacing side)
			{
				NBTTagCompound tag = new NBTTagCompound();
				instance.writeToNBT(tag);
				return tag;
			}

			@Override
			public void readNBT(Capability<ProcessPipeline.Data> capability, ProcessPipeline.Data instance, EnumFacing side, NBTBase nbt)
			{
				if (nbt instanceof NBTTagCompound)
					instance.readFromNBT((NBTTagCompound) nbt);
			}
		}, this);
	}


	private void clear()
	{
		this.cache.clear();
		if (stackCache != null)
			this.stackCache.clear();
		this.varCache.clear();
	}

	@Override
	public Class<? extends Interactive> interfaceType()
	{
		return ProcessPipeline.class;
	}

	@Override
	public ProcessPipeline.Data call() throws Exception
	{
		return null;
	}

	public class Entity implements ProcessPipeline.Data, ITickable, ICapabilityProvider
	{
		private List<Process> processes;
		private List<VarSync> vars;
		private List<Pair<SlotInfo, Var<ItemStack>>> stack;
		private Context context;

		public Entity(List<Process> processes, List<VarSync> vars, List<Pair<SlotInfo, Var<ItemStack>>> stack, Context
				context)
		{
			this.processes = processes;
			this.vars = vars;
			this.stack = stack;
			this.context = context;
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
			for (ITagSerializable var : vars)
				var.readFromNBT(tag);
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			for (ITagSerializable var : vars)
				var.writeToNBT(tag);
		}

		@Override
		public void update()
		{
			for (Process process : processes)
				process.preUpdate(context);
			for (Process process : processes)
				if (process.shouldUpdate(context))
					process.update(context);
			for (Process process : processes)
				process.postUpdate(context);
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == ProcessPipeline.DATA;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			if (capability == ProcessPipeline.DATA)
				return GenericUtil.cast(this);
			return null;
		}
	}

}
