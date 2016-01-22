package net.simplelib.time;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author ci010
 */
@IFMLLoadingPlugin.MCVersion("1.8")
public class TimeLoadingPlg implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		System.out.println("transf");
		return new String[]
				{
						Transformer.class.getName()
				};
	}

	@Override
	public String getModContainerClass()
	{
		return TimeModContainer.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		for (Map.Entry<String, Object> entry : data.entrySet())
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
