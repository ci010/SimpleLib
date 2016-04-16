package test.api;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import test.api.component.block.ComponentBlock;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author ci010
 */
public class BlockAdapter extends BlockOpen
{
	public BlockAdapter(Material materialIn)
	{
		super(materialIn);
	}
//	private ComponentBlock real;
//
//	public BlockAdapter(ComponentBlock real)
//	{
//		super(Property.air);//TODO
////		super(real.getProperty());
//		this.real = real;
//		this.setLightLevel(real.getProperty().getLightLevel())
//				.setResistance(real.getProperty().getResistance())
//				.setHardness(real.getProperty().getHardness());
//	}
//
//	public int getLightOpacity() {return real.getProperty().getLightOpacity();}
//
//	public boolean canReplace(World worldIn, BlockPos pos, @Nullable EnumFacing side, @Nullable ItemStack stack)
//	{
//		return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && real.getAbility().canReplace
//				(worldIn, pos, side, stack);
//	}
//
//	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
//	{
//		return this.canReplace(worldIn, pos, side, null);
//	}
//
//	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
//	{
//		return this.canPlaceBlockOnSide(worldIn, pos, null);
//	}
//
//	public boolean canDropFromExplosion(Explosion explosionIn)
//	{
//		return real.getAbility().canDropFromExplosion
//				(explosionIn);
//	}
//
//	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
//	{
////		return real.getDrops(world, pos, state, fortune);
//	}
//
//	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
//	{
//		return real.getAbility().canSilkHarvest(world, pos, state, player);
//	}
//
//	public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType
//			type)
//	{
//		return real.getAbility().canCreatureSpawn(world, pos, type);
//	}
//
//	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
//	{
//		return false;
//	}
//
//	public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side)
//	{
//		return false;
//	}


}
