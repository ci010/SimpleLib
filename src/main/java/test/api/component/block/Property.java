package test.api.component.block;

import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface Property
{
	Material getMaterial();

	/**
	 * @return resistance to explosions.
	 */
	int getResistance();

	int getHardness();

	int getLightOpacity();

	int getLightLevel();

	interface Sound
	{
		float getVolume();

		float getFrequency();

		ResourceLocation getBreakSound();

		ResourceLocation getStepSound();

		ResourceLocation getPlaceSound();
	}

	interface Material
	{
		boolean isLiquid();

		boolean isSolid();

		boolean isTranslucent();

		boolean blocksMovement();

		boolean isReplaceable();

		boolean isOpaque();

		boolean requiredToolHarvest();

		int getMaterialMobility();

		boolean isFlammable();
	}
}
