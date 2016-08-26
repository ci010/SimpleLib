package api.simplelib.ui.elements;

import api.simplelib.inventory.InventorySlot;
import api.simplelib.inventory.InventorySpace;

/**
 * Doesn't override the click/drag slot function, Just a notation to draw slots.
 *
 * @author ci010
 */
public class ElementInventoryElement extends Element
{
	public ElementInventoryElement(InventorySlot element)
	{
		this.getProperties().num("slot:id").set(element.id());//optional
		this.getProperties().str("slot:name").set(element.name());//required
		this.transform().setSize(18, 18);
	}

	public ElementInventoryElement(InventorySpace space, int xSize)
	{
		this.getProperties().num("slot:id").set(space.id());
		this.getProperties().str("slot:name").set(space.name());
		this.getProperties().num("slot:xSize").set(xSize);//required if size absent
		int ySize = space.getSlots() / xSize;
		this.getProperties().num("slot:ySize").set(ySize);//required if size absent
		this.transform().setSize(xSize * 18, ySize);
	}

}
