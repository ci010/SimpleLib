package test;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author ci010
 */
public class TestBlock extends Block
{
	public static String name = "testBlock";

	public TestBlock()
	{
		super(Material.wood);
		this.setUnlocalizedName("ttt.ddd.zzz");
		this.setCreativeTab(CreativeTabs.tabFood);
		//assets\foodcraft\textures\blocks
	}
}
