package net.simplelib.common.utils;

import com.google.common.collect.Lists;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

/**
 * @author ci010
 */
public class GuiElementPosInfo
{
	private int offset, totalSize;

	private Vector2f iconSize, screenSize, realCompSize;

	public GuiElementPosInfo(Vector2f screenSize, Vector2f iconSize, int leastOffset, int totalSize)
	{
		this.offset = leastOffset;
		this.iconSize = iconSize;
		this.totalSize = totalSize;
		this.screenSize = screenSize;
		this.realCompSize = new Vector2f(iconSize.x + offset, iconSize.y + offset);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof GuiElementPosInfo)
		{
			GuiElementPosInfo o = (GuiElementPosInfo) obj;
			return o.offset == this.offset && o.iconSize.equals(this.iconSize)
					&& o.totalSize == this.totalSize && o.screenSize.equals(this.screenSize);
		}
		return super.equals(obj);
	}

	public static List<Vector2f> format(Vector2f screenSize, Vector2f iconSize, int offset, int totalSize)
	{
		return new GuiElementPosInfo(screenSize, iconSize, offset, totalSize).getPos(1);
	}

	private List<Vector2f> getPos(int line)
	{
		List<Vector2f> lst = Lists.newArrayList();
		int remainder = totalSize % line;
		int countInOneLine = totalSize / line;
		int perLength = (int) (screenSize.x / countInOneLine);
		if (perLength < (iconSize.x + offset))
			return getPos(line + 1);
		float leftX = this.calLeftX(countInOneLine);
		for (int i = 1; i <= line; ++i)
			for (int j = 1; j <= countInOneLine; ++j)
				lst.add(new Vector2f(leftX + j * realCompSize.x, offset + i * realCompSize.y));
		leftX = this.calLeftX(remainder);
		for (int i = 1; i <= remainder; ++i)
			lst.add(new Vector2f(leftX + i * realCompSize.x, offset + (line + 1) * realCompSize.y));
		return lst;
	}

	private float calLeftX(int numInOneLine)
	{
		float center = screenSize.x / 2;
		if (numInOneLine % 2 == 1)
			return (center - iconSize.x / 2) - numInOneLine / 2 * realCompSize.x;
		else
			return (center - offset / 2 - iconSize.x) - (numInOneLine / 2 - 1) * realCompSize.x;
	}
}
