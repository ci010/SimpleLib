package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.utils.TextureInfo;

/**
 * The most basic implementation of {@link GuiComponent} using {@link TextureInfo}.
 * This component just draws the texture in a specific position.
 *
 * @author ci010
 */
public class GuiTextureBlock extends GuiComponent
{
	/**
	 * @param texture The texture will be drawn.
	 * @param x       The texture x position.
	 * @param y       The texture y position.
	 */
	public GuiTextureBlock(TextureInfo texture, int x, int y)
	{
		this.getDrawPipe().addLast(ComponentAPI.DRAW_TEXTURE);
		this.getProperties().property(ComponentAPI.PROP_TEXTURE).set(texture);
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		this.x = x;
		this.y = y;
	}
}
