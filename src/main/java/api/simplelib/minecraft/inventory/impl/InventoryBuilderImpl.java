package api.simplelib.minecraft.inventory.impl;

import api.simplelib.minecraft.inventory.*;
import api.simplelib.utils.ArrayUtil;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * @author ci010
 */
public class InventoryBuilderImpl implements InventoryBuilder
{
	private int currentIdx = 0;
	private InvImpl inv = new InvImpl();
	private EnumMap<EnumFacing, int[]> sideMap = new EnumMap<EnumFacing, int[]>(EnumFacing.class);
	private ArrayList<InventoryElement> elements = new ArrayList<InventoryElement>();

	public InvImpl buildInventory()
	{
		if (currentIdx == 0)
			return null;
		inv.build(sideMap, currentIdx, elements);
		return inv;
	}

	public InventorySpace newSpace(int size, EnumFacing facing, InventoryRule rule)
	{
		if (facing != null)
		{
			int[] newArr = new int[size];
			for (int i = 0; i < size; i++)
				newArr[i] = currentIdx + size;
			if (sideMap.containsKey(facing))
				newArr = ArrayUtil.concat(sideMap.get(facing), newArr);
			sideMap.put(facing, newArr);
		}
		InvSpaceImpl space = new InvSpaceImpl(inv, currentIdx, size);
		space.setRule(rule);
		currentIdx += size;
		elements.add(space);
		return space;
	}

	public InventorySpace newSpace(int size, EnumFacing facing)
	{
		return this.newSpace(size, facing, null);
	}

	public InventorySlot newSlot(EnumFacing facing)
	{
		return this.newSlot(facing, null);
	}

	public InventorySlot newSlot(EnumFacing facing, InventoryRule rule)
	{
		if (facing != null)
		{
			int[] newArr = new int[]{currentIdx};
			if (sideMap.containsKey(facing))
				newArr = ArrayUtil.concat(sideMap.get(facing), newArr);
			sideMap.put(facing, newArr);
		}
		SlotSpaceImpl slotSpace = new SlotSpaceImpl(inv, currentIdx);
		slotSpace.setRule(rule);
		++currentIdx;
		elements.add(slotSpace);
		return slotSpace;
	}

	public void setLayout(Layout layout)
	{
		this.inv.setLayout(layout);
	}
}
