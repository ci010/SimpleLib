package net.simplelib.time;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

/**
 * @author ci010
 */
@IFMLLoadingPlugin.MCVersion("1.8")
public class TimeLoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook
{
	static File src;

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]{TimeTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return "net.simplelib.time.TimeMod";//TimeMod.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return this.getClass().getName();
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		for (Map.Entry<String, Object> entry : data.entrySet())
		{
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		src = (File) data.get("coremodLocation");
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

	@Override
	public Void call() throws Exception
	{
		return null;
	}
}
