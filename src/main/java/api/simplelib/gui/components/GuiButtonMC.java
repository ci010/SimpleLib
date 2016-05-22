package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.node.DrawNode;

/**
 * @author ci010
 */
public class GuiButtonMC extends GuiComponent//TODO finish
{
	public GuiButtonMC(String name, int x, int y, int height, int width)
	{
		this.getDrawPipe().addFirst(ComponentAPI.DRAW_TEXTURE).addLast(ComponentAPI.DRAW_STRING);
		getProperties().property(ComponentAPI.PROP_STRING).set(name);
//		getProperties().property(ComponentAPI.PROP_TEXTURE).set();
//		this.setController(new Controller()
//		{
//			@Override
//			public void onLoad(GuiComponent component)
//			{
//
//			}
//
//			@Override
//			public void draw(GuiComponent component)
//			{
//				for (DrawNode drawNode : component.getDrawPipe())
//				{
//					if (drawNode == ComponentAPI.DRAW_STRING)
//					{
//
//					}
//					else if (drawNode == ComponentAPI.DRAW_TEXTURE)
//					{
//
//					}
//				}
//			}
//
//			@Override
//			public void onRemoved(GuiComponent component)
//			{
//
//			}
//		});
//		this.setPos(x, y).setSize(width, height);
//		this.getMouseListener().addListener(new MouseProperty.Hover()
//		{
//			@Override
//			public void onHover(int mouseX, int mouseY)
//			{
//				getProperties().putCache("hovered", true);
//			}
//
//			@Override
//			public void onRemove(int mouseX, int mouseY)
//			{
//				getProperties().putCache("hovered", false);
//			}
//
//			@Override
//			public float delay()
//			{
//				return 0;
//			}
//		});
	}

//	public void addListener(MouseProperty.MouseClick click)
//	{
//		getMouseListener().addListener(click);
//	}
}
