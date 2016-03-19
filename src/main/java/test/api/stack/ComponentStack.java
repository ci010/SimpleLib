package test.api.stack;

import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * @author ci010
 */
public interface ComponentStack
{
	int getMaxMeta();

	int getCurrentMeta();

	abstract class StackDamageable implements ComponentStack
	{
		public abstract int getRepairCost();
	}

	abstract class StackSubtype implements ComponentStack
	{
	}

	abstract class Stack extends StackSubtype implements CustomInfo
	{
		public abstract int getMaxStackSize();

		public abstract int getCurrentStackSize();

		public final NBTTagCompound getCustomInfo()
		{
			throw new UnsupportedOperationException();
		}
	}

	interface CustomInfo extends ComponentStack
	{
		void set(String id, Object object);

		Object get(String id);
	}

	interface CustomDisplay extends ComponentStack
	{
		String getCustomName();

		List<String> getToolTips();
	}
}
