package api.simplelib.utils;

/**
 * @author ci010
 */
public class Environment
{
	private static boolean DEBUG;

	static
	{
		boolean fail = false;
		try
		{
			Class.forName("net.minecraftforge.gradle.GradleStartCommon");
		}
		catch (ClassNotFoundException e)
		{
			fail = true;
		}
		DEBUG = !fail;
	}

	public static boolean debug()
	{
		return DEBUG;
	}
}
