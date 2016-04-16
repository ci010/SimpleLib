package api.simplelib;

/**
 * @author ci010
 */
public interface VarFactory
{
	Var<Integer> newInteger(String name, int i);

	Var<Float> newFloat(String name, float f);

	Var<Short> newShort(String name, short l);

	Var<Byte> newByte(String name, byte b);

	Var<String> newString(String name, String s);
}
