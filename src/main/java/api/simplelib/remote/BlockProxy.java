package api.simplelib.remote;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;

import java.util.List;

/**
 * @author ci010
 */
//TODO handle foot step sound... basically the material thing
public class BlockProxy extends BlockContainer
{
	public BlockProxy()
	{
		super(Material.rock);
	}

	protected Block getDelegate(IBlockAccess world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityProxy)
			return ((TileEntityProxy) tile).getDelegateBlock();
		return this;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getActualState(state, world, pos);
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isPassable(world, pos);
	}

	@Override
	public boolean isReplaceable(World world, BlockPos pos)
	{
		return getDelegate(world, pos).isReplaceable(world, pos);
	}

	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getBlockHardness(world, pos);
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getMixedBrightnessForBlock(world, pos);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).isBlockSolid(world, pos, side);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getSelectedBoundingBox(world, pos);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity)
	{
		getDelegate(world, pos).addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		return getDelegate(world, pos).getCollisionBoundingBox(world, pos, state);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		getDelegate(world, pos).onBlockDestroyedByPlayer(world, pos, state);
	}

//	@Override
//	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
//	{
//		getDelegate(world, pos).randomDisplayTick(world, pos, state, rand);
//	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		getDelegate(world, pos).onNeighborBlockChange(world, pos, state, neighborBlock);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		getDelegate(world, pos).onBlockAdded(world, pos, state);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		getDelegate(world, pos).dropBlockAsItemWithChance(world, pos, state, chance, fortune);
	}

	@Override
	public void dropXpOnBlockBreak(World world, BlockPos pos, int amount)
	{
		getDelegate(world, pos).dropXpOnBlockBreak(world, pos, amount);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
	{
		return getDelegate(world, pos).collisionRayTrace(world, pos, start, end);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn)
	{
		getDelegate(world, pos).onBlockDestroyedByExplosion(world, pos, explosionIn);
	}

	@Override
	public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack)
	{
		return getDelegate(world, pos).canReplace(world, pos, side, stack);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).canPlaceBlockOnSide(world, pos, side);
	}

//	@Override
//	public EnumWorldBlockLayer getBlockLayer()
//	{
//		return getDelegate(world, pos).getBlockLayer();
//	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return getDelegate(world, pos).canPlaceBlockAt(world, pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{//TODO 
		return getDelegate(world, pos).onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entityIn)
	{
		getDelegate(world, pos).onEntityCollidedWithBlock(world, pos, entityIn);
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDelegate(world, pos).onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer playerIn)
	{
		getDelegate(world, pos).onBlockClicked(world, pos, playerIn);
	}

	@Override
	public Vec3 modifyAcceleration(World world, BlockPos pos, Entity entityIn, Vec3 motion)
	{
		return getDelegate(world, pos).modifyAcceleration(world, pos, entityIn, motion);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		getDelegate(world, pos).setBlockBoundsBasedOnState(world, pos);
	}

	@Override
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		return getDelegate(world, pos).colorMultiplier(world, pos, renderPass);
	}

	@Override
	public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side)
	{
		return getDelegate(world, pos).getWeakPower(world, pos, state, side);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entityIn)
	{
		getDelegate(world, pos).onEntityCollidedWithBlock(world, pos, state, entityIn);
	}

	@Override
	public int getStrongPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side)
	{
		return getDelegate(world, pos).getStrongPower(world, pos, state, side);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
	{
		getDelegate(world, pos).harvestBlock(world, player, pos, state, te);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		getDelegate(world, pos).onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entityIn, float fallDistance)
	{
		getDelegate(world, pos).onFallenUpon(world, pos, entityIn, fallDistance);
	}

	@Override
	public Item getItem(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getItem(world, pos);
	}

	@Override
	public int getDamageValue(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getDamageValue(world, pos);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		getDelegate(world, pos).onBlockHarvested(world, pos, state, player);
	}

	@Override
	public void fillWithRain(World world, BlockPos pos)
	{
		getDelegate(world, pos).fillWithRain(world, pos);
	}

	@Override
	public int getComparatorInputOverride(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getComparatorInputOverride(world, pos);
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getLightValue(world, pos);
	}

	@Override
	public boolean isLadder(IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return getDelegate(world, pos).isLadder(world, pos, entity);
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isNormalCube(world, pos);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return getDelegate(world, pos).doesSideBlockRendering(world, pos, face);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).isSideSolid(world, pos, side);
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isBurning(world, pos);
	}

	@Override
	public boolean isAir(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isAir(world, pos);
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return getDelegate(world, pos).canHarvestBlock(world, pos, player);
	}

	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		return getDelegate(world, pos).removedByPlayer(world, pos, player, willHarvest);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return getDelegate(world, pos).getFlammability(world, pos, face);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return getDelegate(world, pos).isFlammable(world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return getDelegate(world, pos).getFireSpreadSpeed(world, pos, face);
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).isFireSource(world, pos, side);
	}


	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return getDelegate(world, pos).getDrops(world, pos, state, fortune);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return getDelegate(world, pos).canSilkHarvest(world, pos, state, player);
	}

	@Override
	public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
	{
		return getDelegate(world, pos).canCreatureSpawn(world, pos, type);
	}

	@Override
	public boolean isBed(IBlockAccess world, BlockPos pos, Entity player)
	{
		return getDelegate(world, pos).isBed(world, pos, player);
	}

	@Override
	public BlockPos getBedSpawnPosition(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return getDelegate(world, pos).getBedSpawnPosition(world, pos, player);
	}

	@Override
	public void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied)
	{
		getDelegate(world, pos).setBedOccupied(world, pos, player, occupied);
	}

	@Override
	public EnumFacing getBedDirection(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getBedDirection(world, pos);
	}

	@Override
	public boolean isBedFoot(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isBedFoot(world, pos);
	}

	@Override
	public void beginLeavesDecay(World world, BlockPos pos)
	{
		getDelegate(world, pos).beginLeavesDecay(world, pos);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).canSustainLeaves(world, pos);
	}

	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isLeaves(world, pos);
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).canBeReplacedByLeaves(world, pos);
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isWood(world, pos);
	}

	@Override
	public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target)
	{
		return getDelegate(world, pos).isReplaceableOreGen(world, pos, target);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return getDelegate(world, pos).getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
	{
		getDelegate(world, pos).onBlockExploded(world, pos, explosion);
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).canConnectRedstone(world, pos, side);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).canPlaceTorchOnTop(world, pos);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
	{
		return getDelegate(world, pos).getPickBlock(target, world, pos, player);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return getDelegate(world, pos).getPickBlock(target, world, pos);
	}

	@Override
	public boolean isFoliage(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).isFoliage(world, pos);
	}

	@Override
	public boolean addLandingEffects(WorldServer world, BlockPos pos, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
	{
		return getDelegate(world, pos).addLandingEffects(world, pos, iblockstate, entity, numberOfParticles);
	}

//	@Override
//	public boolean addHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer)
//	{
//		return getDelegate(world, pos).addHitEffects(world, target, effectRenderer);
//	}

	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer)
	{
		return getDelegate(world, pos).addDestroyEffects(world, pos, effectRenderer);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
	{
		return getDelegate(world, pos).canSustainPlant(world, pos, direction, plantable);
	}

	@Override
	public void onPlantGrow(World world, BlockPos pos, BlockPos source)
	{
		getDelegate(world, pos).onPlantGrow(world, pos, source);
	}

	@Override
	public boolean isFertile(World world, BlockPos pos)
	{
		return getDelegate(world, pos).isFertile(world, pos);
	}

	@Override
	public int getLightOpacity(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getLightOpacity(world, pos);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity)
	{
		return getDelegate(world, pos).canEntityDestroy(world, pos, entity);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon)
	{
		return getDelegate(world, pos).isBeaconBase(world, pos, beacon);
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		return getDelegate(world, pos).rotateBlock(world, pos, axis);
	}

	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getValidRotations(world, pos);
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return getDelegate(world, pos).getEnchantPowerBonus(world, pos);
	}

	@Override
	public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color)
	{
		return getDelegate(world, pos).recolorBlock(world, pos, side, color);
	}

	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		return getDelegate(world, pos).getExpDrop(world, pos, fortune);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		getDelegate(world, pos).onNeighborChange(world, pos, neighbor);
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).shouldCheckWeakPower(world, pos, side);
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getWeakChanges(world, pos);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return getDelegate(world, pos).getExtendedState(state, world, pos);
	}


	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return getDelegate(world, pos).shouldSideBeRendered(world, pos, side);
	}

	@Override
	public boolean isFullBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityProxy();
	}
}
