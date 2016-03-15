package net.simplelib.deprecated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class DomainMap<K extends DomainMap.Domain, V>
{
	private static List<DomainMap> globle;

	private Map.Entry<K, V>[] entrys;
	private int size;

	private int domain;

	public DomainMap()
	{
		this.domain = globle.size() + 1;
		globle.add(this);
	}

	public V get(K key)
	{
		if (key == null)
			return null;
		Indexer indexer = key.getIndex();
		Map.Entry<K, V> entry = entrys[indexer.getIndex(this.getDomain())];
		return entry.getValue();
	}

	public void put(K key, V value)
	{
		if (key == null)
			return;
		key.getIndex().allocate(this.getDomain(), size++);
		entrys[size] = new Entry(key, value);
	}

	public int getDomain()
	{
		return domain;
	}

	interface Domain
	{
		Indexer getIndex();
	}

	public static class Indexer
	{
		private List<Integer> domains;

		public int getIndex(int domain)
		{
			if (domain > domains.size())
				return -1;
			return domains.get(domain);
		}

		private void allocate(int domain, int index)
		{
			domains.set(domain, index);
		}

		public Indexer(int size)
		{
			domains = new ArrayList<Integer>(size);
		}
	}

	private class Entry implements Map.Entry<K, V>
	{
		private K key;
		private V value;

		Entry(K key, V value)
		{
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey()
		{
			return key;
		}

		@Override
		public V getValue()
		{
			return value;
		}

		@Override
		public V setValue(V value)
		{
			return this.value = value;
		}
	}


}
