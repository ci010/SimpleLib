package api.simplelib.interactive.process;


import api.simplelib.Var;
import api.simplelib.VarFactory;
import api.simplelib.VarNotify;
import api.simplelib.VarSync;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.SlotInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import api.simplelib.utils.ITagSerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * This interface indicates that this {@link Interactive} contains {@link Process}.
 *
 * @author ci010
 */
public interface ProcessPipeline extends Interactive
{
	@CapabilityInject(ProcessPipeline.Data.class)
	Capability<Data> DATA = null;

	void provideProcess(Handler handler, Factory factory);

	/**
	 * All the {@link Var} produced by this factory will be auto-synchronized between server and client side.
	 */
	interface Factory extends VarFactory
	{
		/**
		 * Get the {@link Var<ItemStack>} reference of this {@link SlotInfo} so that you can toggle the item in
		 * that slot.
		 *
		 * @param info The slot info of the slot.
		 *             <p>Don't new this by your self!!!</p>
		 *             Use:
		 *             <ul>
		 *             <li>{@link api.simplelib.interactive.inventory.Inventory.Manager#newSingletonSlot(int, int)}
		 *             <li>{@link api.simplelib.interactive.inventory.Inventory.Manager#newSlotSpace(int, int, int, int)}
		 *             </ul>
		 *             <p>to create a {@link SlotInfo}.</p>
		 * @return The variable of that ItemStack
		 */
		Var<ItemStack> newStack(SlotInfo info);

		<T extends Enum<T>> Var<T> newState(T state, VarNotify.Callback<T> callback);
	}

	/**
	 * @author ci010
	 */
	interface Handler
	{
		/**
		 * Add a new Process to this interactive.
		 *
		 * @param process A new Process.
		 * @return this
		 */
		Handler addProcess(Process process);
	}

	interface Data extends ITagSerializable
	{
		List<Pair<SlotInfo, Var<ItemStack>>> getStack();

		List<VarSync> getVars();
	}
}
