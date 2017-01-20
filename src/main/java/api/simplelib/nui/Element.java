package api.simplelib.nui;

import api.simplelib.nui.std.ElementText;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author ci010
 */
public class Element
{
	private String id;
	private Document.Context context;

	protected Transform transform;

	private List<Element> children;

	private ToolTip toolTip;

	public String getId()
	{
		return id;
	}

	public Document.Context getContext()
	{
		return context;
	}

	public Transform getTransform()
	{
		return transform;
	}

	public List<Element> getChildren()
	{
		return children;
	}

	public static class ToolTip
	{
		ElementText text;
		Policy policy;

		void show() {}

		void hide() {}

		enum Policy
		{
			SHOW_ON_HOVER
		}
	}

	@SideOnly(Side.CLIENT)
	class EventBase extends net.minecraftforge.fml.common.eventhandler.Event
	{
		private Element element;
		private int x, y;

		public Element getElement()
		{
			return element;
		}
	}

	@SideOnly(Side.CLIENT)
	class MouseEnter extends EventBase {}

	@SideOnly(Side.CLIENT)
	class MouseExit extends EventBase {}

	@SideOnly(Side.CLIENT)
	class Click extends EventBase {}

	public static class Transform
	{
		public int x, y, width, height;

		public Transform() {}

		public Transform(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public Transform(Transform transform)
		{
			this.setPos(transform);
			this.setSize(transform);
		}

		public void translate(int x, int y)
		{
			this.x += x;
			this.y += y;
		}

		/**
		 * Set the absolute position of this component.
		 *
		 * @param x The absolute position x of the component.
		 * @param y The absolute position y of the component.
		 * @return this
		 */
		public void setPos(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public void setPos(Transform transform)
		{
			this.x = transform.x;
			this.y = transform.y;
		}

		public void setSize(int width, int height)
		{
			this.height = height;
			this.width = width;
		}

		public void setSize(Transform transform)
		{
			this.width = transform.width;
			this.height = transform.height;
		}

		public boolean container(int x, int y)
		{
			return x > this.x && x < this.x + this.width &&
					y > this.y && y < this.y + this.height;
		}
	}
}
