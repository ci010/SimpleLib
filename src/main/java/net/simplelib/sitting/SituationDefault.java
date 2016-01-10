package net.simplelib.sitting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
public class SituationDefault implements SitHandler.Situation
{
	private static SituationDefault instance;

	public static SituationDefault instance()
	{
		if (instance == null)
			instance = new SituationDefault();
		return instance;
	}


	@Override
	public boolean shouldSit(EntityPlayer player, Block block)
	{
		return player.getHeldItem() == null;
	}


	@Override
	public float offsetVertical()
	{
		return 0f;
	}

	@Override
	public float offsetHorizontal()
	{
		return 0.25f;
	}
}
