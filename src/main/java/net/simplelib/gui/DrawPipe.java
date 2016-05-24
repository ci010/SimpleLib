package net.simplelib.gui;

import api.simplelib.Pipeline;
import api.simplelib.gui.node.DrawNode;

import java.util.ListIterator;

/**
 * @author ci010
 */
public class DrawPipe implements Pipeline<DrawNode>
{
	private Entry head, tail;//TODO finish this
	private transient int size;

	private class Entry
	{
		Entry prev;
		DrawNode node;
		Entry next;
	}

	private Entry getEntry(DrawNode node)
	{
		Entry entry = head;
		while (entry != null)
		{
			if (entry.node.equals(node))
				return entry;
			entry = entry.next;
		}
		return null;
	}

	private Entry getEntryReverse(DrawNode node)
	{
		Entry entry = tail;
		while (entry != null)
		{
			if (entry.node.equals(node))
				return entry;
			entry = entry.prev;
		}
		return null;
	}

	@Override
	public Pipeline<DrawNode> addLast(DrawNode element)
	{
		if (tail == null)
		{
			tail = new Entry();
			tail.node = element;
			tail.prev = head;
		}
		else
		{
			tail.next = new Entry();
			tail.next.prev = tail;
			tail = tail.next;
			tail.node = element;
		}
		size++;
		return this;
	}

	@Override
	public Pipeline<DrawNode> addFirst(DrawNode element)
	{
		if (head == null)
		{
			head = new Entry();
			head.node = element;
			head.next = tail;
		}
		else
		{
			head.prev = new Entry();
			head.prev.next = head;
			head.prev.node = element;
			head = head.prev;
		}
		size++;
		return this;
	}

	@Override
	public Pipeline<DrawNode> setAfter(DrawNode target, DrawNode element)
	{
		Entry t = getEntryReverse(target);
		if (t == null)
			this.addLast(element);
		else
		{

		}
		return this;
	}

	@Override
	public Pipeline<DrawNode> setBefore(DrawNode target, DrawNode element)
	{
		Entry t = getEntry(target);
		if (t == null)
			this.addFirst(element);
		else
		{

		}
		return this;
	}

	@Override
	public void remove(DrawNode element)
	{

	}

	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean contains(Object o)
	{
		if (size == 0)
			return false;
		if (head != null)
			if (head.node.getClass() != o.getClass())
				return false;
		return false;
	}

	@Override
	public void copy(Pipeline<DrawNode> pipeline)
	{

	}

	@Override
	public ListIterator<DrawNode> iterator()
	{
		return null;
	}
}
