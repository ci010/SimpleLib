package api.simplelib.interactive;

import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.interactive.process.ProcessPipeline;
import com.google.common.base.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.simplelib.interactive.block.BlockMachine;

/**
 * @author ci010
 */
public interface Interactive
{
	/**
	 * @return The id of this interactWith element. This should be unique to any other interactWith element.
	 */
	String getId();

	/**
	 * The actual interact behavior.
	 *
	 * @param player The player will be interact.
	 * @param pos    The position this event happened.
	 */
	void interactWith(EntityPlayer player, BlockPos pos);

	/**
	 * Build this interactive. Do something you want to do when all the interactive element initialize.
	 */
	void build();

	interface Builder
	{
		Builder set(Inventory inventory);

		Builder set(ProcessPipeline pipeline);

		Builder setBase(Base base);
	}

	interface Base<T> extends Supplier<T>
	{

	}

	interface KeyBoardBase extends Base<KeyBinding>
	{
	}

	/**
	 * This interface indicates that the
	 * {@link #interactWith(EntityPlayer, BlockPos)} will be called
	 * when {@link net.minecraft.block.Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumFacing, float, float, float)}
	 */
	interface BlockBase extends Base<Block>
	{
		/**
		 * @return The block will activated the {@link #interactWith(EntityPlayer, BlockPos)}.
		 */
	}

//	interface MultiBlock extends Interactive
//	{
//
//	}
}
