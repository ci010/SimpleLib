package test.api.inventory;

/**
 * @author ci010
 */
public interface InventorySpace extends Element
{
	int size();

	InventorySlot get(int slot);

	Layout getLayout();

	void setLayout(Layout layout);
}
