package test;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import api.simplelib.component.ModComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
@ModComponent
public class TestBlock extends BlockStone
{
	public static String name = "testBlock";

	public TestBlock()
	{
//		super(Property.wood);
		this.setUnlocalizedName("ttt.ddd.zzz");
		this.setCreativeTab(CreativeTabs.tabFood);
		this.setHardness(1.5F).setResistance(10.0F);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		assets\foodcraft\textures\blocks
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		System.out.println("onBlockActivated");
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	////////////////////////////////

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		System.out.println("onBlockClicked");
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		System.out.println("onBlockHarvested");
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		System.out.println("removedByPlayer");
		return super.removedByPlayer(world, pos, player, willHarvest);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		System.out.println("breakBlock");
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		System.out.println("onBlockDestroyedByPlayer");
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
	{
		System.out.println("harvestBlock");
		super.harvestBlock(worldIn, player, pos, state, te);
	}


	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		System.out.println("onBlockDestroyedByExplosion");
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	//////////////////////////////////////////////////////////

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		System.out.println("onBlockPlaced");
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		System.out.println("onBlockAdded");
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		System.out.println("onBlockPlacedBy");
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	//////////////////////////////////////////////////////////

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
	{
		System.out.println("onEntityCollidedWithBlock");
		super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		System.out.println("onEntityCollidedWithBlock++");
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	}

//	@Override
//	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
//	{
//		System.out.println("onFallenUpon");
//		super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
//	}
//
//	@Override
//	public void onLanded(World worldIn, Entity entityIn)
//	{
//		System.out.println("onLanded");
//		super.onLanded(worldIn, entityIn);
//	}
}
