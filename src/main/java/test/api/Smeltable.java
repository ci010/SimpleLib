package test.api;

import test.api.stack.ComponentStack;

/**
 * @author ci010
 */
public interface Smeltable//TODO redesign this
{
	/**
	 * Determines the base experience for a player when they remove this item from a furnace slot.
	 * This number must be between 0 and 1 for it to be valid.
	 * This number will be multiplied by the stack size to get the total experience.
	 *
	 * @param stack The item stack the player is picking up.
	 * @return The amount to award for each item.
	 */
	float getSmeltingExperience(ComponentStack stack);

	ComponentStack getOutout(ComponentStack stack);
}
