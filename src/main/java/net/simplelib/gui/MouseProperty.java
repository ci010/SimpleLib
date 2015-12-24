package net.simplelib.gui;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Handle the basic mouse control.
 *
 * @author ci010
 */
public class MouseProperty
{
	private List<MouseClick> click;
	private List<MouseRelease> release;
	private List<Hover> hovers;
	private List<MouseMove> moves;
	private List<Drag> drags;

	public void onClick(int mouseX, int mouseY, int button)
	{
		if (click != null)
			for (MouseClick mouseClick : click)
				mouseClick.onClick(mouseX, mouseY, button);
	}

	public void onRelease(int mouseX, int mouseY, int button)
	{
		if (release != null)
			for (MouseRelease mouseRelease : release)
				mouseRelease.onRelease(mouseX, mouseY, button);
	}

	/**
	 * @param time The time have been hovered in second.
	 */
	public void onHovered(int mouseX, int mouseY, float time)
	{
		if (hovers != null)
			for (Hover hover : hovers)
				if (time > hover.delay())
					hover.onHover(mouseX, mouseY);
	}

	public void onDrag(int mouseX, int mouseY, int button, long milliSecond)
	{
		if (drags != null)
			for (Drag drag : drags)
				drag.onDrag(mouseX, mouseY, button, milliSecond / 1000);
	}

	public void onMove(int mouseX, int mouseY)
	{
		if (moves != null)
			for (MouseMove mouseMove : moves)
				mouseMove.onMove(mouseX, mouseY);
	}

	public MouseProperty addListener(MouseListener listener)
	{
		if (listener instanceof Hover)
		{
			if (hovers == null)
				this.hovers = Lists.newArrayList();
			this.hovers.add((Hover) listener);
		}
		if (listener instanceof MouseClick)
		{
			if (click == null)
				this.click = Lists.newArrayList();
			this.click.add((MouseClick) listener);
		}
		if (listener instanceof MouseMove)
		{
			if (moves == null)
				this.moves = Lists.newArrayList();
			this.moves.add((MouseMove) listener);
		}
		if (listener instanceof MouseRelease)
		{
			if (release == null)
				this.release = Lists.newArrayList();
			this.release.add((MouseRelease) listener);
		}
		if (listener instanceof Drag)
		{
			if (drags == null)
				this.drags = Lists.newArrayList();
			this.drags.add((Drag) listener);
		}
		return this;
	}

	public interface MouseListener
	{}

	public interface Drag extends MouseListener
	{
		void onDrag(int mouseX, int mouseY, int button, float second);
	}

	public interface Hover extends MouseListener
	{
		/**
		 * Waring! This method will only be called when the component is hovered after {@link Hover#delay()} seconds.
		 * <p/>
		 * Therefore, there is no need to check if the mouse position is on this component.
		 * <p/>
		 * If you want a globe mouse listener called on whenever time, use
		 * {@link net.minecraftforge.client.event.MouseEvent}
		 *
		 * @param mouseX The current mouse position in X.
		 * @param mouseY The current mouse position in Y.
		 */
		void onHover(int mouseX, int mouseY);

		/**
		 * @return The number of seconds after the mouse hovered will perform {@link Hover#onHover(int, int)} ()}.
		 */
		float delay();
	}


	/**
	 * button: 1-left, 2-middle, 3-right
	 */
	public interface MouseClick extends MouseListener
	{
		/**
		 * Waring! This method will only be called when the component is clicked.
		 * <p/>
		 * Therefore, there is no need to check if the mouse position is on this component.
		 * <p/>
		 * If you want a globe click listener called on whenever time, use
		 * {@link net.minecraftforge.client.event.MouseEvent}
		 *
		 * @param mouseX The current mouse position in X.
		 * @param mouseY The current mouse position in Y.
		 * @param button The mouse button being pressed.
		 */
		void onClick(int mouseX, int mouseY, int button);
	}

	/**
	 * button: 1-left, 2-middle, 3-right
	 */
	public interface MouseRelease extends MouseListener
	{
		/**
		 * Waring! This method will only be called when the component is released.
		 * <p/>
		 * Therefore, there is no need to check if the mouse position is on this component.
		 * <p/>
		 * If you want a globe mouse listener called on whenever time, use
		 * {@link net.minecraftforge.client.event.MouseEvent}
		 *
		 * @param mouseX The current mouse position in X.
		 * @param mouseY The current mouse position in Y.
		 * @param button The mouse button being released.
		 */
		void onRelease(int mouseX, int mouseY, int button);
	}

	public interface MouseMove extends MouseListener
	{
		void onMove(int mouseX, int mouseY);
	}
}
