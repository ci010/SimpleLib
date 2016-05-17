package api.simplelib.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Mickey
 * @author ci010
 */
public enum FinalFieldUtils
{
	INSTANCE;

	private final Object unsafeObj;
	private final Method
			putObjectMethod,
			objectFieldOffsetMethod,
			staticFieldOffsetMethod,
			staticFieldBaseMethod;

	FinalFieldUtils() throws ReflectiveOperationException
	{
		final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");

		final Field unsafeField = Tools.setAccessible(unsafeClass.getDeclaredField("theUnsafe"));

		unsafeObj = unsafeField.get(null);

		putObjectMethod = unsafeClass.getMethod("putObject", Object.class, long.class, Object.class);

		objectFieldOffsetMethod = unsafeClass.getMethod("objectFieldOffset", Field.class);

		staticFieldOffsetMethod = unsafeClass.getMethod("staticFieldOffset", Field.class);

		staticFieldBaseMethod = unsafeClass.getMethod("staticFieldBase", Field.class);
	}

	public void set(final Object o, final Field field, final Object value) throws Exception
	{
		final Object fieldBase = o;
		final long fieldOffset = (Long) objectFieldOffsetMethod.invoke(unsafeObj, field);

		putObjectMethod.invoke(unsafeObj, fieldBase, fieldOffset, value);
	}

	public void setStatic(final Field field, final Object value) throws Exception
	{
		final Object fieldBase = staticFieldBaseMethod.invoke(unsafeObj, field);
		final long fieldOffset = (Long) staticFieldOffsetMethod.invoke(unsafeObj, field);

		putObjectMethod.invoke(unsafeObj, fieldBase, fieldOffset, value);
	}
}
