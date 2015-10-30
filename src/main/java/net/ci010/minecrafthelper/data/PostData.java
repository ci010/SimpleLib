package net.ci010.minecrafthelper.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class PostData
{
	Block[] blocks;
	Item[] items;
	public String rawName;
	public String varName;

	public PostData(String name)
	{
		this.varName = name;
		this.rawName = this.lowerCase(this.getRaw());
	}

	public PostData with(Block... blocks)
	{
		this.blocks = blocks;
		return this;
	}

	public PostData with(Item... items)
	{
		this.items = items;
		return this;
	}

	String getRaw()
	{
		if (this.varName.startsWith("Block"))
			return this.varName.substring(5);
		if (this.varName.startsWith("Item"))
			return this.varName.substring(4);
		return this.varName;
	}

	String upperCase(String s)
	{
		return String.valueOf(s.charAt(0)).toUpperCase().concat(s.substring(1));
	}

	String lowerCase(String s)
	{
		return String.valueOf(s.charAt(0)).toLowerCase().concat(s.substring(1));
	}

	public Item[] items()
	{
		return this.items;
	}

	public Block[] blocks()
	{
		return this.blocks;
	}
}
