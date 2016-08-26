package api.simplelib.ui.elements;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ci010
 */
public class GuiBorderTexts extends Element
{
	//	private FontRenderer font;
	protected int xLeft, yLeft;
	protected List<CharSequence> keyLines = Lists.newArrayList();

	public GuiBorderTexts()
	{
//		this.getDrawPipe().addLast(ElementAPI.DRAW_BORDER_TEXTS);
//		this.getProperties().property(ElementAPI.PROP_LIST_STRING).set(keyLines);
//		super(type, getName);
	}

	public GuiBorderTexts addTextLine(CharSequence contentKey)
	{
//		VarForward<UpdateList<CharSequence>> property = this.getProperties().property(ElementAPI.PROP_LIST_STRING);
//		if (!property.isPresent())
//			property.set(new ArrayList<CharSequence>());
//		property.var().add(contentKey.toString());
		//TODO consider about the sync...
//		keyLines.add(contentKey);
		return this;
	}


//	@Override
//	public void initGui()
//	{
//		font = Minecraft.getMinecraft().fontRendererObj;
//		contents = Lists.newArrayList();
//		int screenWidth = Minecraft.getMinecraft().currentScreen.width;
//		int screenHeight = Minecraft.getMinecraft().currentScreen.height;
//		for (Object keyLine : this.keyLines)
//			contents.add(Local.trans(keyLine.toString()));
//
//		this.width = 0;
//		for (String s : contents)
//		{
//			int sLength = font.getStringWidth(s);
//			if (sLength > this.width)
//				this.width = sLength;
//			//find the max length of string which will getResource to frame's length.
//		}
//
//		this.height = 8;
//		if (contents.size() > 1)
//			this.height += 2 + (contents.size() - 1) * 10;
//
//		xLeft = x + 12;
//		yLeft = y - 12;
//
//		if (xLeft + this.width > screenWidth)
//			xLeft -= 28 + this.width;
//
//		if (yLeft + this.height + 6 > screenHeight)
//			yLeft = screenHeight - this.height - 6;
//	}
}
