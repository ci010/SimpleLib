package net.simplelib.interactive.process;

import api.simplelib.Var;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.process.Process;
import api.simplelib.interactive.process.ProcessPipeline;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.*;
import java.util.List;

/**
 * @author ci010
 */
public class ProcessHandlerImpl implements ProcessPipeline.Handler
{
	private List<Process> cache;
	private List<Pair<SlotInfo, Var<ItemStack>>> stackCache;

	protected ProcessPipeline pipeline;

	private final boolean available;
	private ProcessPipeline.Factory factory = new ProcessPipeline.Factory()
	{
		@Override
		public Var<ItemStack> newStack(SlotInfo info)
		{
			if (stackCache == null)
				stackCache = Lists.newArrayList();
			Var<ItemStack> holder = new VarItemHolder();
			stackCache.add(Pair.of(info, holder));
			return holder;
		}

		@Override
		public Var<Integer> newInteger(String name, int i)
		{
			return null;
		}

		@Override
		public Var<Float> newFloat(String name, float f)
		{
			return null;
		}

		@Override
		public Var<Short> newShort(String name, short l)
		{
			return null;
		}

		@Override
		public Var<Byte> newByte(String name, byte b)
		{
			return null;
		}

		@Override
		public Var<String> newString(String name, String s)
		{
			return null;
		}
	};

//	protected int numOfProcess, numOfSync, numOfInt, numOfStack;

	public List<Pair<SlotInfo, Var<ItemStack>>> getSlotInfo()
	{
		return stackCache;
	}

	protected ProcessHandlerImpl(ProcessPipeline pipe)
	{
		this.pipeline = pipe;
		pipe.provideProcess(this, factory);
		available = cache.size() != 0;
		cache.clear();
		stackCache.clear();
	}


	/**
	 * Add the actual process to this interactWith component.
	 *
	 * @param process The process will be added.
	 * @return The Process handler.
	 */
	public ProcessHandlerImpl addProcess(Process process)
	{
		if (cache == null)
			cache = Lists.newArrayList();
		cache.add(process);
		return this;
	}

	public boolean available()
	{
		return available;
	}

	public List<Process> buildProcess()
	{
		pipeline.provideProcess(this, factory);
		List<Process> temp = this.cache;
		this.cache.clear();
		this.stackCache.clear();
		return temp;
	}
}
