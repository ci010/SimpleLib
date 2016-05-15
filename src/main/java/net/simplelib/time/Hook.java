package net.simplelib.time;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.objectweb.asm.Type;

/**
 * @author ci010
 */
@SideOnly(Side.CLIENT)
public class Hook
{
	public static final String CLASS = Type.getInternalName(Hook.class),
			GETCOLOR = "getColor", GETCOLOR_DES = "(I)I";

	@SideOnly(Side.CLIENT)
	static TimeMod.WorldProviderModified provider;

	@SideOnly(Side.CLIENT)
	static void init(TimeMod.WorldProviderModified p)
	{
		provider = p;
	}


	@SideOnly(Side.CLIENT)
	public static int getColor(int color)
	{
		if (provider != null)
			return provider.getController().modifyColor(color);
		return color;
	}

	public static void setTime(long time)
	{

	}
}
