package test.api.component.item.capabilities;

/**
 * @author ci010
 */
public interface Damageable
{
	int getDamage();

	int getMaxDamage();

	void damageItem(int damage);
}
