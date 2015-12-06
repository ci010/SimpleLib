package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class GuiDefaultBackground extends GuiComponent
{
	private int width, height, xSize = 176, ySize = 166;

	public GuiDefaultBackground(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public GuiDefaultBackground setSize(int xSize, int ySize)
	{
		this.xSize = xSize;
		this.ySize = ySize;
		return this;
	}

	/**
	 * draw the background from samples resource
	 *
	 * @param xPos   x Position on the Minecraft screen will start to draw
	 * @param yPos   y Position on the Minecraft screen will start to draw
	 * @param length the length of the background you want
	 * @param height the length of the background you want
	 * @param sample the sample resource used to draw
	 */
	public void draw(int xSize, int ySize, int xPos, int yPos, int length, int height, ResourceLocation sample)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(sample);

//		int xSize = ReflectionHelper.getPrivateValue(	GuiContainer.class,
//				gui,
//				"xSize");
//		int ySize = ReflectionHelper.getPrivateValue(	GuiContainer.class,
//				gui,
//				"ySize");

		int gap = 5;
		int dGap = gap * 2;

		int xRemainder = (length - dGap) % 16;
		int xTimes = (length - dGap) / 16;

		int yRemainder = (height - dGap) % 16;

		int yTimes = (height - dGap) / 16;

		// draw content
		for (int i = 0; i <= xTimes; i++)
		{
			for (int j = 0; j <= yTimes; j++)
			{
				if (i == xTimes && j == yTimes)
				{
					this.drawTexturedModalRect(xPos + gap + i * 16,
							yPos + gap + j * 16,
							xSize - 16 - gap,
							gap,
							xRemainder,
							yRemainder);
				}
				else if (i == xTimes)
				{
					this.drawTexturedModalRect(xPos + gap + i * 16,
							yPos + gap + j * 16,
							xSize - 16 - gap,
							gap,
							xRemainder,
							16);

				}
				else if (j == yTimes)
				{
					this.drawTexturedModalRect(xPos + gap + i * 16,
							yPos + gap + j * 16,
							xSize - 16 - gap,
							gap,
							16,
							yRemainder);
				}
				else
					this.drawTexturedModalRect(xPos + gap + i * 16,
							yPos + gap + j * 16,
							xSize - 16 - gap,
							gap,
							16,
							16);

			}
		}

		// draw top left corner
		this.drawTexturedModalRect(xPos, yPos, 0, 0, gap, gap);

		// draw top line
		this.drawTexturedModalRect(xPos + gap,
				yPos,
				gap,
				0,
				length - gap * 2,
				gap);

		// draw top right corner
		this.drawTexturedModalRect(xPos + length - gap,
				yPos,
				xSize - gap,
				0,
				gap,
				gap);

		// draw right line
		this.drawTexturedModalRect(xPos + length - gap,
				yPos + gap,
				xSize - gap,
				gap,
				gap,
				height - gap * 2);

		// right bottom corner
		this.drawTexturedModalRect(xPos + length - gap,
				yPos + height - gap,
				xSize - gap,
				ySize - gap,
				gap,
				gap);

		// draw bottom line
		this.drawTexturedModalRect(xPos + gap,
				yPos + height - gap,
				gap,
				ySize - gap,
				length - gap * 2,
				gap);

		// draw left corner
		this.drawTexturedModalRect(xPos,
				yPos + height - gap,
				0,
				ySize - gap,
				gap,
				gap);

		// draw left line
		this.drawTexturedModalRect(xPos,
				yPos + gap,
				0,
				gap,
				gap,
				height - gap * 2);

	}

	/**
	 * draw the default background sampled from inventory.png
	 *
	 * @param xPos   x Position on the Minecraft screen will start to draw
	 * @param yPos   y Position on the Minecraft screen will start to draw
	 * @param length the length of the background you want
	 * @param height the length of the background you want
	 */
	public void draw(int xSize, int ySize, int xPos, int yPos, int length, int height)
	{
		this.draw(xSize, ySize,
				xPos,
				yPos,
				length,
				height,
				new ResourceLocation("textures/gui/container/inventory.png"));
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
		this.draw(xSize, ySize,
				x,
				y,
				width,
				height,
				new ResourceLocation("textures/gui/container/inventory.png"));
	}
}
