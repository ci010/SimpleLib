package api.simplelib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.List;

/**
 * @author ci010
 */
public abstract class GuiComponent extends Gui implements Drawable
{
	protected int x, y;
	private MouseProperty property;
	protected List<GuiComponent> children;

	/**
	 * @return The type of this component
	 */
	public Type type()
	{
		return Type.back;
	}

	/**
	 * The type of the component. Only used for
	 * {@link GuiContainer#drawGuiContainerBackgroundLayer(float, int, int)}
	 * and {@link GuiContainer#drawGuiContainerForegroundLayer(int, int)}}
	 */
	public enum Type
	{
		front, back
	}

	public boolean hasMouseListener()
	{
		return property != null;
	}

	/**
	 * @return The mouse listener of this component.
	 */
	public MouseProperty getMouseListener()
	{
		if (property == null)
			property = new MouseProperty();
		return property;
	}

	/**
	 * This is just a really simple function which indicates the order of draw of the gui.
	 *
	 * @param component The component may overlap this component.
	 * @return this
	 */
	public GuiComponent add(GuiComponent component)
	{
		this.children.add(component);
		return this;
	}

	protected void setMouseListener(MouseProperty property)
	{
		this.property = property;
	}

	/**
	 * Set the position of this component.
	 *
	 * @param x The position x of the component.
	 * @param y The position y of the component.
	 * @return this
	 */
	public GuiComponent setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * This method will be called when the player open the GuiContainer containing this component.
	 */
	public void initGui()
	{}

	/**
	 * @return The x position of the component.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return The y position of the component.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @return The width of the component.
	 */
	public abstract int getWidth();

	/**
	 * @return The height of the component.
	 */
	public abstract int getHeight();

	protected void bindToTexture(TextureInfo info)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(info.getTexture());
	}

	protected void drawTexture(TextureInfo info)
	{
		this.bindToTexture(info);
		this.drawTexturedModalRect(x, y, info.getU(), info.getV(), info
				.getHeight(), info.getWidth());
	}
}
