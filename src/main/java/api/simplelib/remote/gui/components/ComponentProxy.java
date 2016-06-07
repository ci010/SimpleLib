package api.simplelib.remote.gui.components;

import api.simplelib.remote.gui.plugins.Draggable;
import api.simplelib.remote.gui.plugins.HoverTooltip;
import api.simplelib.remote.gui.plugins.Plugin;

import java.util.List;

/**
 * @author ci010
 */
public class ComponentProxy extends GuiComponent
{
	private GuiComponent component;

	public ComponentProxy(GuiComponent component)
	{
		this.transform = component.transform;
		this.component = component;
		List<Plugin> plugins = component.getPlugins();
		for (Plugin plugin : plugins)
			this.applyPlugin(plugin);
//		if (component.hasMouseListener())
//		{
//			backup = component.getMouseListener();
//			component.setMouseListener(this.getMouseListener());
//		}
		this.applyPlugin(new Draggable());
		this.applyPlugin(new HoverTooltip());
//		this.getMouseListener().addListener(new Draggable(this)).addListener(new HoverTooltip()
//		{
//			@Override
//			public void onRemove(int mouseX, int mouseY)
//			{
//
//			}
//
//			@Override
//			public float delay()
//			{
//				return 0;
//			}
//
//			@Override
//			public GuiBorderTexts getTextLines()
//			{
//				return new GuiBorderTexts().addTextLine(new StringSource("x: ").setSource(new StringSource.Source()
//				{
//					@Override
//					public Object[] getSource()
//					{
//						return new Object[]{ComponentProxy.this.getX()};
//					}
//				})).addTextLine(new StringSource("y: ").setSource(new StringSource.Source()
//				{
//					@Override
//					public Object[] getSource()
//					{
//						return new Object[]{ComponentProxy.this.getY()};
//					}
//				}));
//			}
//		});
	}

	@Override
	protected void finalize() throws Throwable
	{
//		component.setMouseListener(this.backup);
		super.finalize();
	}

//	@Override
//	public void draw()
//	{
//		component.draw();
//	}
}
