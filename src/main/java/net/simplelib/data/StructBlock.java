package net.simplelib.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.simplelib.abstracts.BlockItemStruct;

/**
 * @author ci010
 */
public class StructBlock implements BlockItemStruct
{
	Block block;

	public StructBlock(Block block)
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
