package net.simplelib.interactive.machine;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.simplelib.interactive.InteractiveEntityUpdate;

/**
 * @author ci010
 */
public class BlockMachine extends BlockContainer
{
	MachineMetadata machine;

	protected BlockMachine(Material materialIn)
	{
		super(materialIn);
	}

	public BlockMachine()
	{
		this(Material.iron);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		machine.interactWith(playerIn, pos);
		return true;
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	public final TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDummy().load((InteractiveEntityUpdate) machine.createEntity());
	}
}
