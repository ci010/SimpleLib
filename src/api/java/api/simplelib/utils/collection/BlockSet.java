package api.simplelib.utils.collection;


import api.simplelib.utils.GenericUtil;
import com.google.common.collect.Iterators;
import net.minecraft.block.Block;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public class BlockSet implements Set<Block>
{
	protected Block[] data;
	protected int size;

	public BlockSet()
	{
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
	public boolean contains(Object o)
	{
		return o instanceof Block && data[Block.getIdFromBlock((Block) o)] != null;
	}

	@Override
	public Iterator<Block> iterator()
	{
		return Iterators.forArray(data);
	}

	@Override
	public Object[] toArray()
	{
		return data;
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return GenericUtil.cast(data);
	}

	@Override
	public boolean add(Block nodes)
	{
		this.data[Block.getIdFromBlock(nodes)] = nodes;
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o)
	{
		if (o instanceof Block)
			if (this.contains(o))
			{
				size--;
				this.data[Block.getIdFromBlock((Block) o)] = null;
				return true;
			}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for (Object o : c)
			if (!this.contains(o))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Block> c)
	{
		for (Block block : c)
			this.add(block);
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		for (int i = 0; i < this.data.length; i++)
		{
			if (!c.contains(data[i]))
			{
				this.data[i] = null;
				this.size--;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		for (Object o : c)
			remove(o);
		return false;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.data.length; i++)
			data[i] = null;
		this.size = 0;
	}
}
