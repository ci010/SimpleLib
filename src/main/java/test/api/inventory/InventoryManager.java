package test.api.inventory;


/**
 * @author ci010
 */
public interface InventoryManager
{
	void setLayout(Layout layout);

	Layout getLayout();

	InventorySpace newSpace(int size);

	InventorySlot newSlot();
}
