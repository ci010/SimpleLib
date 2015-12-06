package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import static net.ci010.minecrafthelper.util.GuiUtil.slot;

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

	public enum Type
	{
		front, back
	}

	public boolean hasMouseListener()
	{
		return property != null;
	}

	public MouseProperty getMouseListener()
	{
		if (property == null)
			property = new MouseProperty();
		return property;
	}

	public GuiComponent setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public abstract int getWidth();

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
