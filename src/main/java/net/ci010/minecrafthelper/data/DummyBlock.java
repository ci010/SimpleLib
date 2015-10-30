package net.ci010.minecrafthelper.data;

import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by John on 2015/10/29 0029.
 */
public class DummyBlock implements BlockItemStruct
{
	Block block;

	public DummyBlock(Block block)
	{
		this.block = block;
	}

	@Override
	public void setName(String name)
	{
		block.setUnlocalizedName(name);
	}

	@Override
	public Block[] blocks()
	{
		return new Block[]{this.block};
	}

	@Override
	public Item[] items()
	{
		return null;
	}
}
