package api.simplelib.remote.gui.components;

import api.simplelib.remote.gui.Properties;
import api.simplelib.vars.VarSync;
import net.minecraft.client.gui.FontRenderer;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class GuiText extends GuiComponent
{
	public GuiText(CharSequence key, int x, int y)
	{
//		if (key instanceof RemoteTexts)
//		{
//			RemoteTexts texts = (RemoteTexts) key;
//			List<VarSync> resources = texts.resources();
//			if (resources != null)
//			{
//
//			}
//		}
//		if (key instanceof StringSource)
//			this.getProperties().put(ComponentAPI.PROP_STRING_SRC, (((StringSource) key).source().getSource()));
//		this.getProperties().put(ComponentAPI.PROP_STRING, key);
//		this.setPos(x, y);
//		this.height = 8;
//		String content = key.toString();
//		int width;
//		if (this.width != (width = GuiUtil.font().getStringWidth(content)))
//			this.width = width;
//		this.width = width;
	}


	public GuiText addPlainArg(Object o)
	{
		return this;
	}

	public GuiText addLinkedArg(VarSync o)
	{
		return this;
	}

	public interface Prop extends Properties
	{
		void markDirty(boolean dirty);

		boolean isDirty();

		void setCursor(int cursor);

		int cursor();

		void setStartCursor(int cursor);

		int startCursor();

		void setCacheString(CharSequence s);

		CharSequence getCacheString();

		void setRenderString(String[] arr);

		String[] getRenderString();

		void setRawString(String[] string);

		String[] getRawString();

		FontRenderer getFontRender();

		void setFontRender(FontRenderer render);

		Object[] getArgs();
	}
}
