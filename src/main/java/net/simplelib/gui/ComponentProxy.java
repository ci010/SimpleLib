package net.simplelib.gui;

/**
 * @author ci010
 */
public class ComponentProxy extends GuiComponent
{
	private GuiComponent component;
	private MouseProperty backup;

	public ComponentProxy(GuiComponent component)
	{
		this.component = component;
		if (component.hasMouseListener())
		{
			backup = component.getMouseListener();
			component.setMouseListener(this.getMouseListener());
		}
		this.getMouseListener().addListener(new Draggable(this)).addListener(new HoverTooltip()
		{
			@Override
			public float delay()
			{
				return 0;
			}

			@Override
			public GuiBorderTexts getTextLines()
			{
				return new GuiBorderTexts().addTextLine(new SourceString("x: ").setSource(new SourceString.Source()
				{
					@Override
					public Object[] getSource()
					{
						return new Object[]{ComponentProxy.this.getX()};
					}
				})).addTextLine(new SourceString("y: ").setSource(new SourceString.Source()
				{
					@Override
					public Object[] getSource()
					{
						return new Object[]{ComponentProxy.this.getY()};
					}
				}));
			}
		});
	}

	@Override
	protected void finalize() throws Throwable
	{
		component.setMouseListener(this.backup);
		super.finalize();
	}

	@Override
	public void draw()
	{
		component.draw();
	}

	@Override
	public int getWidth()
	{
		return component.getWidth();
	}

	@Override
	public int getHeight()
	{
		return component.getHeight();
	}
}
