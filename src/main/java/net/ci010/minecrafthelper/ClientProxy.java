package net.ci010.minecrafthelper;

import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
}
