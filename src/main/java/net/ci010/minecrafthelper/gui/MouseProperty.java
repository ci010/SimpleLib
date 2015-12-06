package net.ci010.minecrafthelper.gui;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ci010
 */
public class MouseProperty
{
	List<MouseListener> clicks, release;
	List<Hover> hovers;

	public void onClick(int button)
	{
		if (clicks != null)
			for (MouseListener click : clicks)
				click.actionPerform(button);
	}

	public void onRelease(int button)
	{
		if (release != null)
			for (MouseListener release : clicks)
				release.actionPerform(button);
	}

	public void onHovered()
	{
		if (hovers != null)
			for (Hover hover : hovers)
				hover.onHover();
	}

	public void addClickListener(MouseClick listener)
	{
		if (this.clicks == null)
			clicks = Lists.newArrayList();
		clicks.add(listener);
	}

	public void addReleaseListener(MouseRelease listener)
	{
		if (this.release == null)
			release = Lists.newArrayList();
		release.add(listener);
	}

	public void addHoverListener(Hover listener)
	{
		if (this.hovers == null)
			hovers = Lists.newArrayList();
		hovers.add(listener);
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
