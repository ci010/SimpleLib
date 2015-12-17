package net.ci010.minecrafthelper.util;

/**
 * @author ci010
 */
public class ArrayUtil
{
	public static <T> void concat(T[] arr, T[] arr2)
	{
		int oldLength = arr.length;
		int length = oldLength + arr.length;
		arr = (T[]) new Object[length];
		int i;
		for (i = 0; i < oldLength; ++i)
			arr[i] = arr[i];
		for (; i < length; ++i)
			arr[i] = arr2[i - oldLength];
	}
}
