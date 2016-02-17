package api.simplelib.utils;

/**
 * @author ci010
 */
public class NameFormattor
{
	public static String upperTo_(String name)
	{
		StringBuilder builder = new StringBuilder(name);
		for (int i = 0; i < builder.length(); ++i)
			if (Character.isUpperCase(builder.charAt(i)))
			{
				builder.setCharAt(i, Character.toLowerCase(builder.charAt(i)));
				if (i != 0)
					builder.insert(i, "_");
			}
		return builder.toString();
	}

	public static String _toPoint(String name)
	{
		return name.replace("_", ".");
	}
}
