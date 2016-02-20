package api.simplelib.interactive.process;


import api.simplelib.Var;
import api.simplelib.VarFactory;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.SlotInfo;
import net.minecraft.item.ItemStack;

/**
 * This interface indicates that this {@link Interactive} contains {@link Process}.
 *
 * @author ci010
 */
public interface ProcessPipeline
{
	void provideProcess(Handler handler, Factory factory);

	interface Factory extends VarFactory
	{
		Var<ItemStack> newStack(SlotInfo info);
	}

	/**
	 * @author ci010
	 */
	interface Handler
	{
		Handler addProcess(Process process);
	}
}
