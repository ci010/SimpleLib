package net.simplelib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public abstract class GuiComponent extends Gui implements Drawable
{
	protected int x, y;
	private MouseProperty property;

	public Type type()
	{
		return Type.back;
	}

	public Priority priority()
	{
		return Priority.normal;
	}

	public enum Type
	{
		front, back
	}

	/**
	 * Still a beta feature.
	 * Basically, this will decide the layer of the component.
	 */
	public enum Priority
	{
		high(2), normal(1), low(0);

		Priority(int weight)
		{
			this.weight = weight;
		}

		int weight;

		public int getWeight()
		{
			return weight;
		}
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
