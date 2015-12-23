package net.ci010.minecrafthelper.deprecated;

import net.ci010.minecrafthelper.abstracts.MinecraftComponent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author CI010
 */
public class CompomentBlock extends MinecraftComponent<Block>
{
	public CompomentBlock(Block wrap)
	{
		super(wrap);
	}

	@Override
	public Block setUnlocalizedName(String name)
	{
		return this.getComponent().setUnlocalizedName(name);
	}

	@Override
	public Block setCreativeTab(CreativeTabs tab)
	{
		return this.getComponent().setCreativeTab(tab);
	}
}
