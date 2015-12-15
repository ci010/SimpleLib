package net.ci010.minecrafthelper.gui;

/**
 * @author ci010
 */
public class MouseProperty
{
	private MouseListener click, release;
	private Hover hover;

	public void onClick(int button)
	{
		if (click != null)
			click.actionPerform(button);
	}

	public void onRelease(int button)
	{
		if (release != null)
			release.actionPerform(button);
	}

	public void onHovered(float time)
	{
		if (hover != null && time >= hover.delay())
			hover.onHover();
	}

	public void setClickListener(MouseClick listener)
	{
		click = listener;
	}

	public void setReleaseListener(MouseRelease listener)
	{
		release = listener;
	}

	public void setHoverListener(Hover listener)
	{
		hover = listener;
	}

	public interface Hover
	{
		void onHover();

		int delay();
	}

	/**
	 * button: 1-left, 2-middle, 3-right
	 */
	interface MouseListener
	{
		void actionPerform(int button);
	}

	public interface MouseClick extends MouseListener {}

	public interface MouseRelease extends MouseListener {}
}
