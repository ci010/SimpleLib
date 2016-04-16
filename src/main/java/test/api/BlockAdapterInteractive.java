package test.api;


import api.simplelib.ContextBlockInteract;
import api.simplelib.interactive.BlockInteractive;
import api.simplelib.interactive.Interactive;
import api.simplelib.utils.ContextFactory;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.TileEntityDummy;

import java.util.List;
import java.util.Random;

/**
 * @author ci010
 */
public class BlockAdapterInteractive extends Block
{
	public static class Tile extends BlockAdapterInteractive implements ITileEntityProvider
	{
		private InteractiveMetadata meta;

		public Tile(BlockInteractive real, Interactive interactive)
		{
			super(real, interactive);
			meta = InteractiveMetadata.getInstance(interactive.getId());
		}

		public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
		{
			super.breakBlock(worldIn, pos, state);
			worldIn.removeTileEntity(pos);
		}

		public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
		{
			super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			return tileentity != null && tileentity.receiveClientEvent(eventID, eventParam);
		}

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta)
		{
			return TileEntityDummy.newTileEntity(this.meta.createEntity(ContextFactory.newContext(worldIn)));
		}
	}

	private BlockInteractive real;
	private Interactive interactive;

	public BlockAdapterInteractive(BlockInteractive real, Interactive interactive)
	{
		super(real.getMaterial());
		this.real = real;
		this.interactive = interactive;
	}

	@Override
	public boolean isFullBlock() {return real.isFullBlock();}

	@Override
	public int getLightOpacity() {return real.getLightOpacity();}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isTranslucent() {return real.isTranslucent();}

	@Override
	public int getLightValue() {return real.getLightValue();}

	@Override
	public boolean getUseNeighborBrightness() {return real.getUseNeighborBrightness();}

	@Override
	public Material getMaterial() {return real.getMaterial();}

	@Override
	public MapColor getMapColor(IBlockState state) {return real.getMapColor(state);}

	@Override
	public IBlockState getStateFromMeta(int meta) {return real.getStateFromMeta(meta);}

	@Override
	public int getMetaFromState(IBlockState state) {return real.getMetaFromState(state);}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {return real.getActualState(state, worldIn, pos);}

	@Override
	public Block setStepSound(SoundType sound) {return real.setStepSound(sound);}

	@Override
	public Block setLightOpacity(int opacity) {return real.setLightOpacity(opacity);}

	@Override
	public Block setLightLevel(float value) {return real.setLightLevel(value);}

	@Override
	public Block setResistance(float resistance) {return real.setResistance(resistance);}

	@Override
	public boolean isBlockNormalCube() {return real.isBlockNormalCube();}

	@Override
	public boolean isNormalCube() {return real.isNormalCube();}

	@Override
	public boolean isVisuallyOpaque() {return real.isVisuallyOpaque();}

	@Override
	public boolean isFullCube() {return real.isFullCube();}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {return real.isPassable(worldIn, pos);}

	@Override
	public int getRenderType() {return real.getRenderType();}

	@Override
	public boolean isReplaceable(World worldIn, BlockPos pos) {return real.isReplaceable(worldIn, pos);}

	@Override
	public Block setHardness(float hardness) {return real.setHardness(hardness);}

	@Override
	public Block setBlockUnbreakable() {return real.setBlockUnbreakable();}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {return real.getBlockHardness(worldIn, pos);}

	@Override
	public Block setTickRandomly(boolean shouldTick) {return real.setTickRandomly(shouldTick);}

	@Override
	public boolean getTickRandomly() {return real.getTickRandomly();}

	@Override
	@Deprecated
	public boolean hasTileEntity() {return real.hasTileEntity();}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {return real.getMixedBrightnessForBlock(worldIn, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {return real.shouldSideBeRendered(worldIn, pos, side);}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {return real.isBlockSolid(worldIn, pos, side);}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {return real.getSelectedBoundingBox(worldIn, pos);}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {real.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {return real.getCollisionBoundingBox(worldIn, pos, state);}

	@Override
	public boolean isOpaqueCube() {return real.isOpaqueCube();}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {return real.canCollideCheck(state, hitIfLiquid);}

	@Override
	public boolean isCollidable() {return real.isCollidable();}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {real.randomTick(worldIn, pos, state, random);}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {real.updateTick(worldIn, pos, state, rand);}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {real.randomDisplayTick(worldIn, pos, state, rand);}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {real.onBlockDestroyedByPlayer(worldIn, pos, state);}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {real.onNeighborBlockChange(worldIn, pos, state, neighborBlock);}

	@Override
	public int tickRate(World worldIn) {return real.tickRate(worldIn);}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {real.onBlockAdded(worldIn, pos, state);}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {real.breakBlock(worldIn, pos, state);}

	@Override
	public int quantityDropped(Random random) {return real.quantityDropped(random);}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {return real.getItemDropped(state, rand, fortune);}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {return real.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {real.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);}

	public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {Block.spawnAsEntity(worldIn, pos, stack);}

	@Override
	public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {real.dropXpOnBlockBreak(worldIn, pos, amount);}

	@Override
	public int damageDropped(IBlockState state) {return real.damageDropped(state);}

	@Override
	public float getExplosionResistance(Entity exploder) {return real.getExplosionResistance(exploder);}

	@Override
	public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {return real.collisionRayTrace(worldIn, pos, start, end);}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {real.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);}

	@Override
	public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack) {return real.canReplace(worldIn, pos, side, stack);}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {return real.canPlaceBlockOnSide(worldIn, pos, side);}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {return real.getBlockLayer();}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {return real.canPlaceBlockAt(worldIn, pos);}

//	@Override
//	public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state, EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
//	{
//		ContextBlockInteract context = new ContextBlockInteract()
//		{
//			@Override
//			public EnumFacing getSide()
//			{
//				return side;
//			}
//
//			@Override
//			public float hitX()
//			{
//				return hitX;
//			}
//
//			@Override
//			public float hitY()
//			{
//				return hitY;
//			}
//
//			@Override
//			public float hitZ()
//			{
//				return hitZ;
//			}
//
//			@Override
//			public World getWorld()
//			{
//				return worldIn;
//			}
//
//			@Override
//			public BlockPos getPos()
//			{
//				return pos;
//			}
//
//			@Override
//			public String id()
//			{
//				return "right";
//			}
//		};
//		this.interactive.getAction(context).interactWith(playerIn, context);
//		return real.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
//	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {real.onEntityCollidedWithBlock(worldIn, pos, entityIn);}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {return real.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {real.onBlockClicked(worldIn, pos, playerIn);}

	@Override
	public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {return real.modifyAcceleration(worldIn, pos, entityIn, motion);}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {real.setBlockBoundsBasedOnState(worldIn, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {return real.getBlockColor();}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {return real.getRenderColor(state);}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {return real.colorMultiplier(worldIn, pos, renderPass);}


//	@Override
//	public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {return real.isProvidingWeakPower(worldIn, pos, state, side);}

	@Override
	public boolean canProvidePower() {return real.canProvidePower();}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {real.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);}

//	@Override
//	public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {return real.isProvidingStrongPower(worldIn, pos, state, side);}

	@Override
	public void setBlockBoundsForItemRender() {real.setBlockBoundsForItemRender();}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {real.harvestBlock(worldIn, player, pos, state, te);}

//	@Override
//	@Deprecated
//	public boolean canSilkHarvest() {return real.canSilkHarvest();}

	@Override
	public ItemStack createStackedBlock(IBlockState state) {return real.createStackedBlock(state);}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {return real.quantityDroppedWithBonus(fortune, random);}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {real.onBlockPlacedBy(worldIn, pos, state, placer, stack);}

	@Override
	public Block setUnlocalizedName(String name) {return real.setUnlocalizedName(name);}

	@Override
	public String getLocalizedName() {return real.getLocalizedName();}

	@Override
	public String getUnlocalizedName() {return real.getUnlocalizedName();}

	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {return real.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);}

	@Override
	public boolean getEnableStats() {return real.getEnableStats();}

//	@Override
//	public Block disableStats() {return real.disableStats();}

	@Override
	public int getMobilityFlag() {return real.getMobilityFlag();}

	@Override
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue() {return real.getAmbientOcclusionLightValue();}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {real.onFallenUpon(worldIn, pos, entityIn, fallDistance);}

	@Override
	public void onLanded(World worldIn, Entity entityIn) {real.onLanded(worldIn, entityIn);}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {return real.getItem(worldIn, pos);}

	@Override
	public int getDamageValue(World worldIn, BlockPos pos) {return real.getDamageValue(worldIn, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {real.getSubBlocks(itemIn, tab, list);}

	@Override
	public Block setCreativeTab(CreativeTabs tab) {return real.setCreativeTab(tab);}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {real.onBlockHarvested(worldIn, pos, state, player);}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {return real.getCreativeTabToDisplayOn();}

	@Override
	public void fillWithRain(World worldIn, BlockPos pos) {real.fillWithRain(worldIn, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFlowerPot() {return real.isFlowerPot();}

	@Override
	public boolean requiresUpdates() {return real.requiresUpdates();}

	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {return real.canDropFromExplosion(explosionIn);}

	@Override
	public boolean isAssociatedBlock(Block other) {return real.isAssociatedBlock(other);}

	public static boolean isEqualTo(Block blockIn, Block other) {return Block.isEqualTo(blockIn, other);}

	@Override
	public boolean hasComparatorInputOverride() {return real.hasComparatorInputOverride();}

	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos) {return real.getComparatorInputOverride(worldIn, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {return real.getStateForEntityRender(state);}

	@Override
	public BlockState createBlockState()
	{
		return real.createBlockState();
	}

	@Override
	public BlockState getBlockState() {return real.getBlockState();}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumOffsetType getOffsetType() {return real.getOffsetType();}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {return real.getLightValue(world, pos);}

	@Override
	public boolean isLadder(IBlockAccess world, BlockPos pos, EntityLivingBase entity) {return real.isLadder(world, pos, entity);}

	@Override
	public boolean isNormalCube(IBlockAccess world, BlockPos pos) {return real.isNormalCube(world, pos);}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {return real.isSideSolid(world, pos, side);}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos) {return real.isBurning(world, pos);}

	@Override
	public boolean isAir(IBlockAccess world, BlockPos pos) {return real.isAir(world, pos);}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {return real.canHarvestBlock(world, pos, player);}

	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {return real.removedByPlayer(world, pos, player, willHarvest);}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {return real.getFlammability(world, pos, face);}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {return real.isFlammable(world, pos, face);}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {return real.getFireSpreadSpeed(world, pos, face);}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {return real.isFireSource(world, pos, side);}

	@Override
	public boolean hasTileEntity(IBlockState state) {return real.hasTileEntity(state);}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {return real.createTileEntity(world, state);}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {return real.quantityDropped(state, fortune, random);}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {return real.getDrops(world, pos, state, fortune);}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {return real.canSilkHarvest(world, pos, state, player);}

	@Override
	public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {return real.canCreatureSpawn(world, pos, type);}

	@Override
	public boolean isBed(IBlockAccess world, BlockPos pos, Entity player) {return real.isBed(world, pos, player);}

	@Override
	public BlockPos getBedSpawnPosition(IBlockAccess world, BlockPos pos, EntityPlayer player) {return real.getBedSpawnPosition(world, pos, player);}

	@Override
	public void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied) {real.setBedOccupied(world, pos, player, occupied);}

	@Override
	public EnumFacing getBedDirection(IBlockAccess world, BlockPos pos) {return real.getBedDirection(world, pos);}

	@Override
	public boolean isBedFoot(IBlockAccess world, BlockPos pos) {return real.isBedFoot(world, pos);}

	@Override
	public void beginLeavesDecay(World world, BlockPos pos) {real.beginLeavesDecay(world, pos);}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos) {return real.canSustainLeaves(world, pos);}

	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos pos) {return real.isLeaves(world, pos);}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, BlockPos pos) {return real.canBeReplacedByLeaves(world, pos);}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {return real.isWood(world, pos);}

	@Override
	public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target) {return real.isReplaceableOreGen(world, pos, target);}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {return real.getExplosionResistance(world, pos, exploder, explosion);}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {real.onBlockExploded(world, pos, explosion);}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {return real.canConnectRedstone(world, pos, side);}

	@Override
	public boolean canPlaceTorchOnTop(IBlockAccess world, BlockPos pos) {return real.canPlaceTorchOnTop(world, pos);}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {return real.getPickBlock(target, world, pos, player);}

	@Override
	@Deprecated
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {return real.getPickBlock(target, world, pos);}

	@Override
	public boolean isFoliage(IBlockAccess world, BlockPos pos) {return real.isFoliage(world, pos);}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {return real.addHitEffects(worldObj, target, effectRenderer);}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {return real.addDestroyEffects(world, pos, effectRenderer);}

	@Override
	public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {return real.canSustainPlant(world, pos, direction, plantable);}

	@Override
	public void onPlantGrow(World world, BlockPos pos, BlockPos source) {real.onPlantGrow(world, pos, source);}

	@Override
	public boolean isFertile(World world, BlockPos pos) {return real.isFertile(world, pos);}

	@Override
	public int getLightOpacity(IBlockAccess world, BlockPos pos) {return real.getLightOpacity(world, pos);}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {return real.canEntityDestroy(world, pos, entity);}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {return real.isBeaconBase(worldObj, pos, beacon);}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {return real.rotateBlock(world, pos, axis);}

	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos) {return real.getValidRotations(world, pos);}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos) {return real.getEnchantPowerBonus(world, pos);}

	@Override
	public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {return real.recolorBlock(world, pos, side, color);}

	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {return real.getExpDrop(world, pos, fortune);}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {real.onNeighborChange(world, pos, neighbor);}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side) {return real.shouldCheckWeakPower(world, pos, side);}

	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {return real.getWeakChanges(world, pos);}

	@Override
	public void setHarvestLevel(String toolClass, int level) {real.setHarvestLevel(toolClass, level);}

	@Override
	public void setHarvestLevel(String toolClass, int level, IBlockState state) {real.setHarvestLevel(toolClass, level, state);}

	@Override
	public String getHarvestTool(IBlockState state) {return real.getHarvestTool(state);}

	@Override
	public int getHarvestLevel(IBlockState state) {return real.getHarvestLevel(state);}

	@Override
	public boolean isToolEffective(String type, IBlockState state) {return real.isToolEffective(type, state);}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {return real.getExtendedState(state, world, pos);}

	@Override
	public boolean canRenderInLayer(EnumWorldBlockLayer layer) {return real.canRenderInLayer(layer);}

	@Override
	public List<ItemStack> captureDrops(boolean start) {return real.captureDrops(start);}
}
