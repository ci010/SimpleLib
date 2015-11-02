package net.ci010.minecrafthelper.data;

import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by John on 2015/10/29 0029.
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
