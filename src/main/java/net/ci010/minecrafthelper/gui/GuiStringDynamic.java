package net.ci010.minecrafthelper.gui;

import net.ci010.minecrafthelper.util.GuiUtil;
import net.minecraft.util.StatCollector;

/**
 * @author ci010
 */
public class GuiStringDynamic extends GuiString
{
	protected Source source;

	public GuiStringDynamic(String key, int x, int y, Source source)
	{
		super(key, x, y);
		this.source = source;
	}

	public interface Source
	{
		Object[] getValue();
	}

	@Override
	public void draw()
	{
		if (this.width == 0)
		{
			if (GuiUtil.font() == null)
			{
				System.out.println("WTF");
				return;
			}
			if (this.key == null)
			{
				System.out.println("holy shit");
				return;
			}
		}
		this.drawString(GuiUtil.font(), StatCollector.translateToLocalFormatted(key, this.source.getValue()), x, y, color);
	}
}
