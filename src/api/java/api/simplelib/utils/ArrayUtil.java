package api.simplelib.utils;

/**
 * @author ci010
 */
public class ArrayUtil
{
	/**
	 * Concat the second array to the first array.
	 *
	 * @param arr  The first array.
	 * @param arr2 The second array.
	 * @param <T>  The type of these array.
	 */
	public static <T> void concat(T[] arr, T[] arr2)
	{
		int oldLength = arr.length;
		int length = oldLength + arr.length;
		arr = GenericUtil.cast(new Object[length]);
		int i;
		for (i = 0; i < oldLength; ++i)
			arr[i] = arr[i];
		for (; i < length; ++i)
			arr[i] = arr2[i - oldLength];
	}

	/**
	 * Switch two element in an array.
	 *
	 * @param arr    The array will be handled.
	 * @param first  The first element position.
	 * @param second The second element position.
	 * @param <T>    The type of the array.
	 */
	public static <T> void switchElement(T[] arr, int first, int second)
	{
		T temp = arr[first];
		arr[first] = arr[second];
		arr[second] = temp;
	}
}
