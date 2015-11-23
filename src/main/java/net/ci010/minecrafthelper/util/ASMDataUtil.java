package net.ci010.minecrafthelper.util;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
public class ASMDataUtil
{
	public static String getModId(ASMDataTable.ASMData data)
	{
		return data.getCandidate().getContainedMods().get(0).getModId();
	}

	public static <Anno extends Annotation> Anno getAnnotation(ASMDataTable.ASMData data, Class<Anno> clz)
	{
		return getClass(data).getAnnotation(clz);
	}

	public static Class<?> getClass(ASMDataTable.ASMData data)
	{
		Class<?> c = null;
		try
		{
			c = Class.forName(data.getClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return c;
	}
}
