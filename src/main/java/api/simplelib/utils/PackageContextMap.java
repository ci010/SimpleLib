package api.simplelib.utils;

import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
@Beta
public class PackageContextMap<V>
{
	private int size;
	private List<Entry> rootList;
	private V global;

	public PackageContextMap()
	{
		this.rootList = Lists.newArrayList();
	}

	public PackageContextMap(V globalContext)
	{
		this();
		this.global = globalContext;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public void putGlobal(V context)
	{
		this.global = context;
	}

	public V getGlobalContext()
	{
		return global;
	}

	public V getContext(Package p)
	{
		if (p == null)
			return null;
		return get(p.getName());
	}

	public V getContext(Class clz)
	{
		if (clz == null)
			return null;
		return get(clz.getName());
	}


	public V putContext(Class clz, V value)
	{
		if (clz == null)
			return null;
		if (value == null)
			return null;
		put0(clz.getName(), value);
		return value;
	}

	public V putContext(Package p, V value)
	{
		if (p == null)
			return null;
		if (value == null)
			return null;
		put0(p.getName(), value);
		return value;
	}

	protected V get(String node)
	{
		if (size == 0)
			return null;
		Entry entry = getRootEntry(node);
		while (entry != null)
		{
			int r = node.compareTo(entry.node);
			if (r < 0)
				if (entry.left != null)
					entry = entry.left;
				else
					return entry.value;
			else if (r > 0)
				if (entry.right != null)
					entry = entry.right;
				else
					return entry.value;
			else
				return entry.value;
		}
		return global;
	}

	protected Entry getRootEntry(String pkg)
	{
		Entry entry = null;
		int idx = pkg.indexOf(".");
		String root = idx > 0 ? pkg.substring(0, idx) : pkg;
		for (Entry e : rootList)
		{
			idx = e.node.indexOf(".");
			if (idx > 0 && e.node.substring(0, idx).equals(root))
				entry = e;
			else if (e.node.equals(root))
				entry = e;
		}
		return entry;
	}

	protected V put0(String node, V value)
	{
		Entry entry = getRootEntry(node);
		if (entry == null)
		{
			rootList.add(newEntry(node, value));
			Collections.sort(rootList);
		}
		else
			while (entry.left != null || entry.right != null)
			{
				int r = node.compareTo(entry.node);
				if (r < 0)
					if (entry.left != null)
						entry = entry.left;
					else
						entry.left = newEntry(node, value);
				else if (r > 0)
					if (entry.right != null)
						entry = entry.right;
					else
						entry.right = newEntry(node, value);
				else
					throw new IllegalArgumentException("Duplicated add! This should not happen!");
			}
		return value;
	}


	protected Entry newEntry(String key, V value)
	{
		Entry entry = new Entry();
		entry.node = key;
		entry.value = value;
		size++;
		return entry;
	}

	protected class Entry implements Comparable<Entry>
	{
		protected String node;
		protected V value;
		protected Entry left, right;

		@Override
		public int compareTo(Entry o)
		{
			return node.compareTo(o.node);
		}
	}
}
