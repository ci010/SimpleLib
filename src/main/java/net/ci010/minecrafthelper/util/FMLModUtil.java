package net.ci010.minecrafthelper.util;

import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * @author ci010
 */
public class FMLModUtil
{
	public static ModContainer getModContainer(String modid)
	{
		return Loader.instance().getIndexedModList().get(modid);
	}

	public static void setActiveContainer(ModContainer container)
	{
		ReflectionHelper.setPrivateValue(LoadController.class,
				(LoadController) ReflectionHelper.getPrivateValue(Loader.class,
						Loader.instance(),
						"modController"),
				container,
				"activeContainer");
	}
}
