package net.simplelib.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.simplelib.abstracts.BlockItemStruct;

/**
 * @author ci010
 */
public class StructItem implements BlockItemStruct
{
	Item item;

	public StructItem(Item item)
	{
		this.item = item;
	}

	@Override
	public void setName(String name)
	{
		this.item.setUnlocalizedName(name);
	}

	@Override
	public Block[] blocks()
	{
		return null;
	}

	@Override
	public Item[] items()
	{
		return new Item[]{this.item};
	}
}
