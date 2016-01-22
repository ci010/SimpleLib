package net.simplelib.time;

import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;

/**
 * @author ci010
 */
public class Hook
{
	public static int getColor(double temp, double humi)
	{
		WorldProvider provider = Minecraft.getMinecraft().theWorld.provider;
		if (provider instanceof TimeModContainer.WorldProviderModified)
		{
			TimeModContainer.WorldProviderModified p = (TimeModContainer.WorldProviderModified) provider;
			TimeController controller = p.getController();
//			controller.getP
		}
		return 0;
	}
}
