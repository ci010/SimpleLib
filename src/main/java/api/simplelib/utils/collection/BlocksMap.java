package api.simplelib.utils.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;

import java.util.*;

/**
 * @author ci010
 */
public class BlocksMap<T> implements Map<Block, T>
{
	private Map.Entry<Block, T>[] entries;
	private int size;

	private class Entry implements Map.Entry<Block, T>
	{
		private int id;
		private T data;
//		private Entry next;

		public Entry(Block block, T data)
		{
			this.id = Block.getIdFromBlock(block);
			this.data = data;
//			next = null;
		}

		@Override
		public Block getKey()
		{
			return Block.getBlockById(id);
		}

		@Override
		public T getValue()
		{
			return data;
		}

		@Override
		public T setValue(T value)
		{
			return data = value;
		}
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size != 0;
	}

	@Override
	public boolean containsKey(Object key)
	{
		if (key == null)
			return false;
		if (key instanceof Block)
			return entries[Block.getIdFromBlock((Block) key)] != null;
		return false;
	}

	@Override
	public boolean containsValue(Object value)
	{
		if (value == null)
			return false;
		Map.Entry e;
		for (int i = 0; i < this.entries.length; i++)
			if ((e = entries[i]) != null)
				if (value.equals(e.getValue()))
					return true;
		return false;
	}

	@Override
	public T get(Object key)
	{
		if (key instanceof Block)
			return this.entries[Block.getIdFromBlock((Block) key)].getValue();
		return null;
	}

	@Override
	public T put(Block key, T value)
	{
		this.entries[Block.getIdFromBlock(key)] = new Entry(key, value);
		this.size++;
		return value;
	}

	@Override
	public T remove(Object key)
	{
		if (key instanceof Block)
		{
			int id = Block.getIdFromBlock((Block) key);
			Map.Entry<Block, T> entry = this.entries[id];
			this.entries[id] = null;
			this.size--;
			return entry.getValue();
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends Block, ? extends T> m)
	{
		for (Map.Entry<? extends Block, ? extends T> entry : m.entrySet())
			this.put(entry.getKey(), entry.getValue());
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.entries.length; i++)
			entries[i] = null;
	}

	@Override
	public Set<Block> keySet()
	{
		return null;
	}

	@Override
	public Collection<T> values()
	{
		return null;
	}

	@Override
	public Set<Map.Entry<Block, T>> entrySet()
	{
		return Sets.newHashSet(this.entries);
	}

}
