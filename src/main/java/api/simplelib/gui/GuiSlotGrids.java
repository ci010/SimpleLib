package api.simplelib.gui;

import api.simplelib.utils.GuiUtil;

/**
 * @author ci010
 */
public class GuiSlotGrids extends GuiComponent
{
	private int width, height, xCount, yCount;

	public GuiSlotGrids(int x, int y, int xCount, int yCount)
	{
		this.setPos(x, y);
		this.xCount = xCount;
		this.yCount = yCount;
		this.width = xCount * 18;
		this.height = yCount * 18;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void draw()
	{
		TextureInfo slot = GuiUtil.slot;
		int length = slot.getWidth(), height = slot.getHeight();
		GuiUtil.bindToTexture(slot);
		for (int xi = 0; xi < this.xCount; ++xi)
			for (int yi = 0; yi < this.yCount; ++yi)
				this.drawTexturedModalRect(xi * length, yi * height, slot.getU(), slot.getV(), length, height);
	}
}
