package api.simplelib.ui.elements;

import api.simplelib.ui.ElementAPI;
import api.simplelib.utils.TextureInfo;
import api.simplelib.vars.VarRef;
import net.minecraft.util.ResourceLocation;

/**
 * The bar-like Component.
 * The effect of this is like the progress bar in furnace.
 *
 * @author ci010
 */
public class ElementBar extends Element
{
	public ElementBar(VarRef<Float> target, TextureInfo bar, Direction direction)
	{
		getProperties().enm(Direction.class, "bar:dir").set(direction);
		((VarRef.Delegate<Number>) getProperties().num("bar:progress")).setDelegate(target);
		getProperties().var(new ResourceLocation("bar:texture"), ElementAPI.TEXTURE).set(bar);
	}

	public enum Direction
	{
		RIGHT, LEFT, UP, DOWN
	}
}
