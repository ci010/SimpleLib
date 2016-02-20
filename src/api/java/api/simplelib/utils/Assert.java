package api.simplelib.utils;

import com.google.common.base.Preconditions;
import net.simplelib.common.CommonLogger;

/**
 * @author ci010
 */
public class Assert
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

	public static <T> T notNullWeak(T obj)
	{
		try
		{
			return Preconditions.checkNotNull(obj);
		}
		catch (NullPointerException e)
		{
			if (DEBUG)
				throw e;
			else
			{
				CommonLogger.fatal("The object is null! This might affect something but may not crash the game " +
						"directly!");
				return null;
			}
		}
	}
}
