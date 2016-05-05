package api.simplelib.minecraft.inventory;

import api.simplelib.common.NotNull;
import net.simplelib.common.Vector2i;

/**
 * @author ci010
 */
public interface Layout
{
	Vector2i NULL = new Vector2i(-1, -1);

	@NotNull
	Vector2i getPos(int id);
}
