package net.simplelib.interactive.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class BlockInteractive extends BlockContainer
{
	protected BlockInteractive(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}
}
