package net.ci010.minecrafthelper.abstracts;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * This interface is used for the custom data structure containing blocks/items.
 * 
 * @author CI010
 * 
 */
public interface BlockItemStruct
{
	/**
	 * Set the unlocalized names for the block and item if you need.
	 * 
	 * @param name
	 *            The name is from the variable name.
	 */
	void setName(String name);

	/**
	 * @return All the blocks contained in this data structure.
	 */
	Block[] blocks();

	/**
	 * @return All the items contained in this data structure.
	 */
	Item[] items();
}
