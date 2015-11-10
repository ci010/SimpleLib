package net.ci010.minecrafthelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public boolean isClient() {return false;}

	public boolean isClientSide() {return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;}

	public boolean isSinglePlayer()
	{
		System.out.println(Minecraft.getMinecraft().isGamePaused());
		return Minecraft.getMinecraft().isSingleplayer();
	}
}
