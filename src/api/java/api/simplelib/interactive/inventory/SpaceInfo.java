package api.simplelib.interactive.inventory;

/**
 * @author ci010
 */
public interface SpaceInfo extends SlotInfo
{
	SlotInfo get(int x, int y);

	int row();

	int column();

	int count();
}
