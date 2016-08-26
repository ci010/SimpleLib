package api.simplelib.ui.elements;

import api.simplelib.inventory.Inventory;
import api.simplelib.inventory.InventoryElement;
import api.simplelib.inventory.InventorySlot;
import api.simplelib.inventory.InventorySpace;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author ci010
 */
public class Elements
{
	public static Element newText(String text, Object... args)
	{
		return new ElementText(text, false, args);
	}

	public static Element newTextByUnlocalizedKey(String key, Object... args)
	{
		return new ElementText(key, true, args);
	}

	public static Element wrapInventory(@Nullable Element parent, Inventory inventory)
	{
		Collection<? extends InventoryElement> all = inventory.getAll();
		return wrapInventory(parent, all.toArray(new InventoryElement[all.size()]));
	}

	public static Element wrapInventory(@Nullable Element parent, InventoryElement... elements)
	{
//		if (parent == null)
//			parent = new Element();
//		if (elements == null)
//			return null;
//		if (elements.length == 1)
//			parent = new ElementInventoryElement(elements[0]);
//		else for(InventoryElement element : elements)
//			parent.add(new ElementInventoryElement(element));
//		return parent;
		return null;
	}

	public static Element wrapInventory(InventoryElement... elements)
	{
		return wrapInventory(null, elements);
	}
}
