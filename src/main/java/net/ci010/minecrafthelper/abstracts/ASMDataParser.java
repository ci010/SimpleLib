package net.ci010.minecrafthelper.abstracts;

import java.lang.annotation.Annotation;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;

public abstract class ASMDataParser
{
	public void parse(Set<ASMData> data)
	{
		for (ASMData d : data)
			parse(d);
	}

	protected String getId(ASMData data)
	{
		for (ModContainer c : Loader.instance().getModList())
			if (c.getSource().equals(data.getCandidate().getModContainer()))
				return c.getModId();
		return null;
	}

	protected <T extends Annotation> T getAnnotation(ASMData data, Class<T> clz)
	{
		return this.getClass(data).getAnnotation(clz);
	}

	protected Class<?> getClass(ASMData data)
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

	protected abstract void parse(ASMData data);
}
