package api.simplelib.vars;

import api.simplelib.seril.ITagSerializer;

/**
 * @author ci010
 */
public interface VarSyncFactory
{
	VarSync<Integer> newInteger(String name, int i);

	VarSync<Float> newFloat(String name, float f);

	VarSync<Short> newShort(String name, short l);

	VarSync<Byte> newByte(String name, byte b);

	VarSync<String> newString(String name, String s);

	VarSync<Double> newDouble(String name, double d);

	<T extends Enum<T>> VarSync<T> newEnum(String name, T e, Class<T> enumClass);

	<T> VarSync<T> newVar(T init, ITagSerializer<T> serializer);
}
