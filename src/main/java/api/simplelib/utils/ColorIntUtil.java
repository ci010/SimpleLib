package api.simplelib.utils;

/**
 * @author ci010
 */
public class ColorIntUtil
{
	public static int changeAlpha(int rgb, int alpha)
	{
		return ((alpha & 0xFF) << 24) | (rgb & 0xFFFFFF);
	}

	public static int getAlpha(int rgb)
	{
		return (rgb >> 24) & 0xFF;
	}

	public static int getRed(int rgb)
	{
		return (rgb >> 16) & 0xFF;
	}
}
