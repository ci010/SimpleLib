package api.simplelib.interactive;

import api.simplelib.interactive.base.BlockBase;
import com.google.common.base.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * The interface to define an interactive thing.
 * <p>Basically, it could be a machine link furnace which have some processes and can handle something.</p>
 * <p>Or, you could make it like chest which just contain a inventory for storage.</p>
 * <p>Even more, this could be a non-entity interactive which means you could have a cloud data set to calculate or
 * just store some data.</p>
 * <p>Indeed, it's easy to toggle an {@link Action} just by a command or a key binding.<p/>
 * <li>To make this contains an inventory like chest, implements this with the interface
 * {@link api.simplelib.interactive.inventory.Inventory}
 * <li>To make this calculate some data and processes like furnace, implements this with the interface
 * {@link api.simplelib.interactive.process.ProcessPipeline}
 * <li>To register this, annotated this by {@link ModInteractive}.</li>
 *
 * @author ci010
 * @see api.simplelib.interactive.inventory.Inventory
 * @see api.simplelib.interactive.process.ProcessPipeline
 * @see ModInteractive
 */
public interface Interactive
{
	/**
	 * @return The base of this interactive.
	 */
	Base getBase();

	/**
	 * @return The id of this interactWith element. This should be unique to any other interactWith element.
	 */
	String getId();

	/**
	 * @return The action will actually control the behavior of interaction.
	 */
	Action getAction();

	/**
	 * Build this interactive. Do something you want to do when all the interactive element initialize.
	 */
	void build();

	/**
	 * The base means that the basic implementation of this interactive action.
	 * <p>This should supply a specific data which is needed to fulfill a custom requirement to interactive.</p>
	 * <p>For example,
	 * {@link BlockBase} which means
	 * {@link Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumFacing, float, float, float)}
	 * will trigger the {@link Action}, so it need a specific block as an additional information.</p>
	 * <p/>
	 * <p>To customize a new Base, you need to create an interface extends Base and also register this with a</p>
	 *
	 * @param <T>
	 */
	interface Base<T> extends Supplier<T>
	{

	}

	/**
	 * The action that will performance the actual behavior of this interaction.
	 *
	 * @author ci010
	 */
	interface Action
	{
		/**
		 * The actual interactive behavior.
		 *
		 * @param player The player will be interact.
		 * @param pos    The position this event happened.
		 */
		void interactWith(EntityPlayer player, BlockPos pos);
	}
}
