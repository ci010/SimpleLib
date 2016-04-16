package test.impl;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import test.api.component.block.BlockModules;
import test.api.component.block.ComponentBlock;
import test.api.component.block.module.Break;
import test.api.component.block.module.RainDrop;

import java.util.List;
import java.util.Random;

/**
 * @author ci010
 */
public class BlockCompiled extends Block
{
	private ComponentBlock delegate;
	private IProperty[] properties;
	private Break moduleBreak = BlockModules.BREAK.getDefaultState();
	private RainDrop rainDrop = BlockModules.RAIN_DROP.getDefaultState();

	public BlockCompiled(BlockCompiledInfo info)
	{
		super(info.material());
		this.delegate = info.delegate();
		properties = info.defaultProperties();
		Comparable[] values = info.getValues();
		IBlockState state = this.blockState.getBaseState();
		for (int i = 0; i < properties.length; i++)
			state.withProperty(properties[i], values[i]);
		this.setDefaultState(state);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, properties);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return super.damageDropped(state);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return super.getActualState(state, worldIn, pos);
	}

	@Override
	public void fillWithRain(World worldIn, BlockPos pos)
	{
		rainDrop.onRainDropOn(WorldImpl.of(worldIn), pos);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer()
	{
		return super.getBlockLayer();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos)
	{
		return super.getComparatorInputOverride(worldIn, pos);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn()
	{
		return super.getCreativeTabToDisplayOn();
	}

	@Override
	public int getDamageValue(World worldIn, BlockPos pos)
	{
		return super.getDamageValue(worldIn, pos);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return super.getDrops(world, pos, state, fortune);
	}

	@Override
	public boolean getEnableStats()
	{
		return super.getEnableStats();
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return super.getEnchantPowerBonus(world, pos);
	}

	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		return super.getExpDrop(world, pos, fortune);
	}

	@Override
	public float getExplosionResistance(Entity exploder)
	{
		return super.getExplosionResistance(exploder);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return super.getExtendedState(state, world, pos);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return super.getFireSpreadSpeed(world, pos, face);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return super.getFlammability(world, pos, face);
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return super.getHarvestLevel(state);
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return super.getHarvestTool(state);
	}

//	@Override
//	public Item getItem(World worldIn, BlockPos pos)
//	{
//		return super.getItem(worldIn, pos);
//	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int getLightOpacity()
	{
		return delegate.getProperty().getLightOpacity();
	}

	@Override
	public int getLightOpacity(IBlockAccess world, BlockPos pos)
	{//TODO check this
		return delegate.getProperty().getLightOpacity();
	}

	@Override
	public int getLightValue()
	{
		return delegate.getProperty().getLightLevel();
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
	{
		return super.getLightValue(world, pos);
	}

	@Override
	public String getLocalizedName()
	{
		return super.getLocalizedName();
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return super.getMapColor(state);
	}

	@Override
	public Material getMaterial()
	{
		return super.getMaterial();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return super.getMetaFromState(state);
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos)
	{
		return super.getMixedBrightnessForBlock(worldIn, pos);
	}

	@Override
	public int getMobilityFlag()
	{
		return super.getMobilityFlag();
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return super.getOffsetType();
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
	{
		return super.getPickBlock(target, world, pos, player);
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos)
	{
		return super.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
	}

	@Override
	public int getRenderColor(IBlockState state)
	{
		return super.getRenderColor(state);
	}

	@Override
	public int getRenderType()
	{
		return super.getRenderType();
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
	{
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public IBlockState getStateForEntityRender(IBlockState state)
	{
		return super.getStateForEntityRender(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return super.getStateFromMeta(meta);
	}

	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
	{
		return super.getStrongPower(worldIn, pos, state, side);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		super.getSubBlocks(itemIn, tab, list);
	}

	@Override
	public boolean getTickRandomly()
	{
		return super.getTickRandomly();
	}

	@Override
	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName();
	}

	@Override
	public boolean getUseNeighborBrightness()
	{
		return super.getUseNeighborBrightness();
	}

	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos)
	{
		return super.getValidRotations(world, pos);
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos)
	{
		return super.getWeakChanges(world, pos);
	}

	@Override
	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
	{
		return super.getWeakPower(worldIn, pos, state, side);
	}

	/////////////////////////////TODO Break Module
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

	/////////////////////////////


	@Override
	public boolean hasComparatorInputOverride()
	{
		return super.hasComparatorInputOverride();
	}

	@Override
	public boolean hasTileEntity()
	{
		return super.hasTileEntity();
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return super.hasTileEntity(state);
	}

	@Override
	public boolean isAir(IBlockAccess world, BlockPos pos)
	{
		return super.isAir(world, pos);
	}

	@Override
	public boolean isAssociatedBlock(Block other)
	{
		return super.isAssociatedBlock(other);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		return super.isBeaconBase(worldObj, pos, beacon);
	}

	@Override
	public boolean isBed(IBlockAccess world, BlockPos pos, Entity player)
	{
		return super.isBed(world, pos, player);
	}

	@Override
	public boolean isBedFoot(IBlockAccess world, BlockPos pos)
	{
		return super.isBedFoot(world, pos);
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return super.isBlockNormalCube();
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		return super.isBlockSolid(worldIn, pos, side);
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos)
	{
		return super.isBurning(world, pos);
	}

	@Override
	public boolean isCollidable()
	{
		return super.isCollidable();
	}

	@Override
	public boolean isFertile(World world, BlockPos pos)
	{
		return super.isFertile(world, pos);
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
	{
		return super.isFireSource(world, pos, side);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return super.isFlammable(world, pos, face);
	}

	@Override
	public boolean isFlowerPot()
	{
		return super.isFlowerPot();
	}

	@Override
	public boolean isFoliage(IBlockAccess world, BlockPos pos)
	{
		return super.isFoliage(world, pos);
	}

	@Override
	public boolean isFullBlock()
	{
		return super.isFullBlock();
	}

	@Override
	public boolean isFullCube()
	{
		return super.isFullCube();
	}

	@Override
	public boolean isLadder(IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return super.isLadder(world, pos, entity);
	}

	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos pos)
	{
		return super.isLeaves(world, pos);
	}

	@Override
	public boolean isNormalCube()
	{
		return super.isNormalCube();
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, BlockPos pos)
	{
		return super.isNormalCube(world, pos);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return super.isOpaqueCube();
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return super.isPassable(worldIn, pos);
	}

	@Override
	public boolean isReplaceable(World worldIn, BlockPos pos)
	{
		return super.isReplaceable(worldIn, pos);
	}

	@Override
	public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target)
	{
		return super.isReplaceableOreGen(world, pos, target);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return super.isSideSolid(world, pos, side);
	}

	@Override
	public boolean isToolEffective(String type, IBlockState state)
	{
		return super.isToolEffective(type, state);
	}

	@Override
	public boolean isTranslucent()
	{
		return super.isTranslucent();
	}

	@Override
	public boolean isVisuallyOpaque()
	{
		return super.isVisuallyOpaque();
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return super.isWood(world, pos);
	}

	@Override
	public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion)
	{
		return super.modifyAcceleration(worldIn, pos, entityIn, motion);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		return super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
	}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
	{
		super.onBlockExploded(world, pos, explosion);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
	}

	@Override
	public void onLanded(World worldIn, Entity entityIn)
	{
		super.onLanded(worldIn, entityIn);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		super.onNeighborChange(world, pos, neighbor);
	}

	@Override
	public void onPlantGrow(World world, BlockPos pos, BlockPos source)
	{
		super.onPlantGrow(world, pos, source);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return super.quantityDropped(random);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return super.quantityDropped(state, fortune, random);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return super.quantityDroppedWithBonus(fortune, random);
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.randomDisplayTick(worldIn, pos, state, rand);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
		super.randomTick(worldIn, pos, state, random);
	}

	@Override
	public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color)
	{
		return super.recolorBlock(world, pos, side, color);
	}

	@Override
	public boolean requiresUpdates()
	{
		return super.requiresUpdates();
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		return super.rotateBlock(world, pos, axis);
	}

	@Override
	public void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied)
	{
		super.setBedOccupied(world, pos, player, occupied);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		super.setBlockBoundsBasedOnState(worldIn, pos);
	}

	@Override
	public void setBlockBoundsForItemRender()
	{
		super.setBlockBoundsForItemRender();
	}

	@Override
	public Block setBlockUnbreakable()
	{
		return super.setBlockUnbreakable();
	}

	@Override
	public Block setCreativeTab(CreativeTabs tab)
	{
		return super.setCreativeTab(tab);
	}

	@Override
	public Block setHardness(float hardness)
	{
		return super.setHardness(hardness);
	}

	@Override
	public void setHarvestLevel(String toolClass, int level)
	{
		super.setHarvestLevel(toolClass, level);
	}

	@Override
	public void setHarvestLevel(String toolClass, int level, IBlockState state)
	{
		super.setHarvestLevel(toolClass, level, state);
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return super.shouldCheckWeakPower(world, pos, side);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		return super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public int tickRate(World worldIn)
	{
		return super.tickRate(worldIn);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
	}

}
