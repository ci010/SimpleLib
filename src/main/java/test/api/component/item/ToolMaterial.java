package test.api.component.item;

/**
 * @author ci010
 */
public interface ToolMaterial
{
	/**
	 * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
	 */
	int getMaxUses();

	/**
	 * The strength of this tool material against blocks which it is effective against.
	 */
	float getEfficiencyOnProperMaterial();

	/**
	 * Returns the damage against a given entity.
	 */
	float getDamageVsEntity();

	/**
	 * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
	 */
	int getHarvestLevel();

	/**
	 * Return the natural enchantability factor of the material.
	 */
	int getEnchantability();
}
