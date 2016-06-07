package api.simplelib.remote.gui.components;

import api.simplelib.remote.gui.ComponentAPI;
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
		this.getProperties().put(ComponentAPI.PROP_TEXTURE, texture);
		this.setPosRelative(x, y);
		transform.setSize(texture.getWidth(), texture.getHeight());
	}
}
