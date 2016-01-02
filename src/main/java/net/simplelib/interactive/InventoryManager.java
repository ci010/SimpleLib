package net.simplelib.interactive;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.simplelib.HelperMod;
import net.simplelib.registry.annotation.field.Name;

import java.util.List;

/**
 * @author ci010
 */
public class InventoryManager
{
	protected List<Info> infoList = Lists.newArrayList();
	private ImmutableMap<String, Info> infoMap;

	public boolean isEmpty()
	{
		return infoList.size() == 0;
	}

	/**
	 * Create a space of slot.
	 * In this space, the slots will be allocated according to row * column shape.
	 * The first slot will be on (x,y); the second will be on just right of the first, and so on.
	 * <p>In this situation, if you want a {@link VarItemHolder} to point a specific
	 * slot, please an array show the order of the slot associated with VarItemHolder.</p>
	 *
	 * @param spaceId The id of the space.
	 * @param x       The left x position of all the slots.
	 * @param y       The top y position of all the slots.
	 * @param row     The numbers of row.
	 * @param column  The numbers of column.
	 * @return this.
	 */
	public SpaceInfo newSlotSpace(String spaceId, int x, int y, int row, int column, String... namespace)
	{
		if (row == 1 && column == 1)
			HelperMod.LOG.warn("Attempting create a single slot space!\n Highly recommond to use newSingletonSlot to " +
					"handle this;");
		SpaceInfo spaceInfo = new SpaceInfo(spaceId, x, y, row, column).setNamespace(namespace);
		infoList.add(spaceInfo);
		return spaceInfo;
	}

	/**
	 * This is the easy solution. You directly assign a slot to a specific position.
	 * <p>To point this slot, the {@link VarItemHolder} can just be named same as
	 * this slotId.</p> <p>Or you can use
	 * {@link Name} which has the same {@link Name#value()} of
	 * this slot id to assign that holder to this slot. You don't need to initialized the field.
	 * </p>
	 * For example, if you create a singleton slot named "test". Your VarItemHolder will look like this:
	 * <p>class Xxxx implements Process<p>
	 * {
	 * </p>
	 * VarItemHolder test;
	 * <p/>
	 * //implementations
	 * <p/>
	 * ...
	 * <p/>
	 * }
	 *
	 * @param slotId The id of the slot.
	 * @param x      The x position of the slot.
	 * @param y      The y position of the slot.
	 * @return this
	 */
	public Info newSingletonSlot(String slotId, int x, int y)
	{
		Info info = new Info(slotId, x, y);
		infoList.add(info);
		return info;
	}

	protected ImmutableMap<String, Info> getInfoMap()
	{
		if (this.infoMap == null)
		{
			ImmutableMap.Builder<String, Info> builder = ImmutableMap.builder();
			for (Info info : this.infoList)
				builder.put(info.id, info);
			this.infoMap = builder.build();
		}
		return this.infoMap;
	}

	protected ImmutableMap<String, Inventory> apply()
	{
		List<Inventory> temp = Lists.newArrayList();
		Inventory inv;
		int count = 0;
		List<String> namespaceCache = Lists.newArrayList();
		for (Info info : this.infoList)
			if (info instanceof SpaceInfo)
			{
				SpaceInfo spaceInfo = (SpaceInfo) info;
				temp.add(new Inventory(spaceInfo.id, spaceInfo.count, spaceInfo.namespace, spaceInfo.rule));
			}
			else
			{
				++count;
				namespaceCache.add(info.id);
			}
		if (count > 0)
		{
			inv = new Inventory("default", count, namespaceCache.toArray(new String[count]), Inventory.COMMON);
			temp.add(inv);
		}
		ImmutableMap.Builder<String, Inventory> builder = ImmutableMap.builder();
		for (Inventory i : temp)
			builder.put(i.getCommandSenderName(), i);
		return builder.build();
	}

	public class Info
	{
		protected String id;
		protected int x, y, guiSlotSize;
		protected InventoryRule rule;

		public Info(String id, int x, int y)
		{
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public Info setSlotSize(int size)
		{
			this.guiSlotSize = size;
			return this;
		}

		public Info applyRule(InventoryRule rule)
		{
			this.rule = rule;
			return this;
		}
	}

	public class SpaceInfo extends Info
	{
		protected int row, column, count;
		protected String[] namespace;

		protected SpaceInfo(String id, int x, int y, int row, int column)
		{
			super(id, x, y);
			this.row = row;
			this.column = column;
			this.count = row * column;
			this.guiSlotSize = 16;
		}

		protected SpaceInfo setNamespace(String[] namespace)
		{
			this.namespace = namespace;
			return this;
		}
	}
}
