package test.api.block;

/**
 * @author ci010
 */
public interface Material
{
	Property getProperty();

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

		String getBreakSound();

		String getStepSound();

		String getPlaceSound();
	}

	interface Property
	{
		boolean isLiquid();

		boolean isSolid();

		boolean blocksLight();

		boolean blocksMovement();

		boolean isReplaceable();

		boolean isOpaque();

		boolean requiredToolHarvest();

//		int getMaterialMobility();
	}

	Property transparent = new Property()
	{
		@Override
		public boolean isLiquid()
		{
			return false;
		}

		@Override
		public boolean isSolid()
		{
			return false;
		}

		@Override
		public boolean blocksLight()
		{
			return false;
		}

		@Override
		public boolean blocksMovement()
		{
			return false;
		}

		@Override
		public boolean isReplaceable()
		{
			return true;
		}

		@Override
		public boolean isOpaque()
		{
			return false;
		}

		@Override
		public boolean requiredToolHarvest()
		{
			return false;
		}
	};
}
