package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.utils.TextureInfo;
import com.google.common.base.Supplier;

/**
 * The bar-like Component.
 * The effect of this is like the progress bar in furnace.
 *
 * @author ci010
 */
public class GuiBar extends GuiComponent
{
	private GuiTextureBlock background;
	private int x, y;

	public GuiBar(Supplier<Float> target, TextureInfo bar, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.getProperties().property(ComponentAPI.PROP_TEXTURE).set(bar);
		this.getProperties().cache("supplier").set(target);
	}

	/**
	 * @param direction The direction of the bar will move.
	 * @return this
	 */
	public GuiBar setDirection(Direction direction)
	{
		this.getProperties().property(ComponentAPI.PROP_DIRECTION).set(direction);
		return this;
	}

	/**
	 * @param background The texture of the background of the bar.
	 * @param xOffset    The offset related to the x position of this bar.
	 * @param yOffset    The offset related to the y position of this bar.
	 * @return this
	 */
	public GuiBar setBackground(TextureInfo background, int xOffset, int yOffset)
	{
		this.background = new GuiTextureBlock(background, x + xOffset, y + yOffset);
		return this;
	}

	@Override
	public int getWidth()
	{
		return background.getWidth();
	}

	@Override
	public int getHeight()
	{
		return background.getHeight();
	}

	public enum Direction
	{
		toRight, toLeft, toTop, toBottom;
	}
}
