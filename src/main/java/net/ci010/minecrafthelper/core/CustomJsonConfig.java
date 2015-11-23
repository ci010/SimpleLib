package net.ci010.minecrafthelper.core;

import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.Loader;

import java.util.Map;

/**
 * Created by John on 2015/10/27 0027.
 */
class CustomJsonConfig
{
	//TODO complete this class
	Map<String, DataCollection> map = Maps.newHashMap();

	public void registerJsonConfig(String name)
	{
		Loader.instance().activeModContainer().getModId();
	}

	class DataCollection
	{
		String modid, name;
	}
}
