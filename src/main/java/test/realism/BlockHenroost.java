package test.realism;

import api.simplelib.component.ModComponent;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

/**
 * @author ci010
 */
@ModComponent
public class BlockHenroost extends Block
{
	public static final IProperty<Integer> TYPE = PropertyInteger.create("age", 0, 6);

	public BlockHenroost(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(Material.plants);
		this.setBlockBounds(0.3f, 0, 0.3f, 0.7f, 0.5f, 0.7f);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		int stage = getStage(state);
		if (stage != 0 && rand.nextInt() > 16)
			worldIn.setBlockState(pos, state.withProperty(TYPE, stage + 1), 2);
		if (stage > 5)
		{
			EntityChicken entityChicken = new EntityChicken(worldIn);
			entityChicken.setGrowingAge(-12000);
			entityChicken.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), rand.nextFloat(), 0.01f);
			worldIn.spawnEntityInWorld(entityChicken);
			worldIn.setBlockState(pos, state.withProperty(TYPE, 0));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (playerIn.getHeldItem().getItem() == Items.egg)
			if (getStage(state) == 0)
			{
				IItemHandler handler = playerIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
				handler.extractItem(playerIn.inventory.currentItem, 1, false);
				worldIn.setBlockState(pos, state.withProperty(TYPE, 1));
			}
		return true;
	}

	public int getStage(IBlockState state)
	{
		return state.getValue(TYPE);
	}


//	protected boolean canPlaceBlockOn(Block ground)
//	{
//		return ground.getMaterial() == Material.grass || ground.getMaterial() == Material.wood;
//	}

}
