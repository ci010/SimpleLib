package net.simplelib.gui;

import java.util.List;

/**
 * @author ci010
 */
public class Container
{
	private List<Element> nodes;
	private Controller controller;

	void init()
	{
		for (Element node : nodes) initNode(node);
	}

	void initNode(Element e)
	{
		for (Element child : e.children)
			child.setPos(e.x + child.x, e.y + child.y);
	}

	void draw(Element element)
	{
		if (element.getController() == null)
			controller.draw(element, element.x, element.y);
		else if (this.controller != null)
			element.getController().draw(element, element.x, element.y);
		if (element.children != null)
			for (Element child : element.children)
				draw(child);
	}

}
