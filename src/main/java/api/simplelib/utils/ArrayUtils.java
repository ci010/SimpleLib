package api.simplelib.utils;

/**
 * @author ci010
 */
public class ArrayUtils
{
	public static int[] ensureCapacity(int[] arr, int length)
	{
		if (arr.length < length)
		{
			int[] newArr = new int[length];
			System.arraycopy(arr, 0, newArr, 0, arr.length);
			return newArr;
		}
		return arr;
	}

	public static <T> T[] ensureCapacity(T[] arr, int length)
	{
		if (arr.length < length)
		{
			T[] newArr = newArray(length);
			System.arraycopy(arr, 0, newArr, 0, arr.length);
			return newArr;
		}
		return arr;
	}

	public static int[] concat(int[] a, int[] b)
	{
		int[] newArr = new int[a.length + b.length];
		System.arraycopy(a, 0, newArr, 0, a.length);
		System.arraycopy(b, 0, newArr, a.length, b.length);
		return newArr;
	}

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

	public static <T> T[] newArray(int size)
	{
		return GenericUtil.cast(new Object[size]);
	}

	public static <T> T[] newArray(T... obj)
	{
		return obj;
	}
}
