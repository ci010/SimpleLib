package net.ci010.minecrafthelper.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * @author ci010
 */
public class ContainerWrap extends Container
{
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	public static class Var
	{
		private int value;

		public Var(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return this.value;
		}

		public void setValue(int value)
		{
			this.value = value;
		}
	}
}
