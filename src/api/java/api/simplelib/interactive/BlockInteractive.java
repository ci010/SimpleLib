package api.simplelib.interactive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import java.util.List;

/**
 * The block used by {@link api.simplelib.interactive.base.BlockBase}.
 * <p>You can implement all the features of the block I think...</p>
 *
 * @author ci010
 * @see api.simplelib.interactive.base.BlockBase
 */
public abstract class BlockInteractive extends Block
{
	private String modid;

	public BlockInteractive(Material materialIn)
	{
		super(materialIn);
		this.modid = Loader.instance().activeModContainer().getModId();
	}

	@Override
	public BlockState createBlockState()
	{
		return super.createBlockState();
	}

	@Override
	public ItemStack createStackedBlock(IBlockState state)
	{
		return super.createStackedBlock(state);
	}

	@Override
	public List<ItemStack> captureDrops(boolean start)
	{
		return super.captureDrops(start);
	}

	public String getModId()
	{
		return modid;
	}

	public abstract String getName();
}
