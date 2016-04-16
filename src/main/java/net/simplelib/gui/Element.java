package net.simplelib.gui;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ci010
 */
public abstract class Element implements Controller.Target
{
	private Controller controller;
	protected List<Element> children = Lists.newArrayList();
	protected int x, y;

	public Element addElement(Element element)
	{
		this.children.add(element);
		return this;
	}

	public Controller getController()
	{
		return controller;
	}

	public Element setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public void setController(Controller controller)
	{
		this.controller = controller;
	}
}
