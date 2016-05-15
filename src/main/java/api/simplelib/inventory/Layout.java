package api.simplelib.inventory;

import api.simplelib.utils.NotNull;
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
