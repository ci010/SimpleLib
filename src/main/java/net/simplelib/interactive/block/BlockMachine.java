package net.simplelib.interactive.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import api.simplelib.interactive.Interactive;
import net.simplelib.interactive.InteractiveEntityUpdate;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.machine.TileEntityDummy;

/**
 * @author ci010
 */
public abstract class BlockMachine extends BlockContainer
{
	private Interactive interactive;

	protected BlockMachine(Material materialIn)
	{
		super(materialIn);
	}

	public BlockMachine(Interactive interactive)
	{
		this(Material.iron);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.interactive.interactWith(playerIn, pos);
		return true;
	}

	public abstract InteractiveMetadata getMachine();

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	public final TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDummy().load((InteractiveEntityUpdate) getMachine().createEntity(worldIn));
	}
}
