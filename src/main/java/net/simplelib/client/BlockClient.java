package net.simplelib.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author ci010
 */
public interface BlockClient
{
	boolean isTranslucent();

	int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos);

	boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side);

	AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos);

	void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand);

	EnumWorldBlockLayer getBlockLayer();

	int getBlockColor();

	int getRenderColor(IBlockState state);

	int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass);

	float getAmbientOcclusionLightValue();

	CreativeTabs getCreativeTabToDisplayOn();

	IBlockState getStateForEntityRender(IBlockState state);

	Block.EnumOffsetType getOffsetType();
}
