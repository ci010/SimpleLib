package api.simplelib.ui.elements;

import api.simplelib.ui.ElementAPI;
import api.simplelib.utils.TextureInfo;
import net.minecraft.util.ResourceLocation;

/**
 * The most basic implementation of {@link Element} using {@link TextureInfo}.
 * This component just draws the texture in a specific position.
 *
 * @author ci010
 */
public class TextureBlock extends Element
{
	/**
	 * @param texture The texture will be drawn.
	 * @param x       The texture x position.
	 * @param y       The texture y position.
	 */
	public TextureBlock(TextureInfo texture, int x, int y)
	{
		this.getProperties().var(new ResourceLocation("texture_block:texture"), ElementAPI.TEXTURE).set(texture);
		this.setPosRelative(x, y);
//		this.getDrawPipe().addLast()
		transform.setSize(texture.getWidth(), texture.getHeight());
	}
}
