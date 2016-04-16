package api.simplelib;

import net.minecraft.util.EnumFacing;

/**
 * @author ci010
 */
public interface ContextBlockInteract extends Context
{
	EnumFacing getSide();

	float hitX();

	float hitY();

	float hitZ();
}
