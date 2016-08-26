package api.simplelib.utils;

import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ci010
 */
@Beta
public class PackageModIdMap extends PackageContextMap<String>
{
	public String getModid(Package pkg)
	{
		return getContext(pkg);
	}

	public String getModid(Class<?> clz)
	{
		return get(clz.getClass().getPackage().getName());
	}

	public void put(String node, String modid)
	{
		super.put0(node, modid);
	}
}
