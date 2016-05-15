package net.simplelib.deprecated;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class Plugin implements IFMLLoadingPlugin, IFMLCallHook
{
	private File src;

	@Override
	public void injectData(Map<String, Object> data)
	{
		System.out.println("inject data");
		src = (File) data.get("coremodLocation");
	}

	public File getSorceFile()
	{
		return src;
	}
}
