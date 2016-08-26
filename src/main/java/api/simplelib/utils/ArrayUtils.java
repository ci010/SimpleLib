package api.simplelib.utils;

/**
 * @author ci010
 */
public class ArrayUtils
{
	public static final Object[] EMPTY_ARR = new Object[0];

	public static <T> T[] emptyArray()
	{
		return TypeUtils.cast(EMPTY_ARR);
	}

	public static <T> T[] shrink(T[] arr)
	{
		int count = 0;
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != null) ++count;
		if (count == 0)
			return emptyArray();
		T[] objects = newArrayWithCapacity(count);
		count = 0;
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != null) objects[count++] = arr[i];
		return objects;
	}

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
			return resize(arr, length);
		return arr;
	}

	public static <T> T[] resize(T[] arr, int length)
	{
		T[] newArr = newArrayWithCapacity(length);
		System.arraycopy(arr, 0, newArr, 0, length);
		return newArr;
	}

	public static int[] iterate(int from, int to)
	{
		int[] ints = new int[to - from];
		for (int i = 0; i < ints.length; i++)
			ints[i] = i + from;
		return ints;
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

	public static <T> T[] newArrayWithCapacity(int size)
	{
		return TypeUtils.cast(new Object[size]);
	}

	public static <T> T[] newArray(T... obj)
	{
		if (obj != null)
		{
			T[] arr = newArrayWithCapacity(obj.length);
			System.arraycopy(obj, 0, arr, 0, obj.length);
			return arr;
		}
		return newArrayWithCapacity(0);
	}
}
