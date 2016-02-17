package api.simplelib.entity;

import api.simplelib.IVar;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
public interface VarWatching<T> extends IVar<T>, ITagSerial
{
	interface Factory
	{
		VarWatching<Integer> newInteger(int i);

		VarWatching<Float> newFloat(float f);

		VarWatching<Short> newShort(short l);

		VarWatching<Byte> newByte(byte b);

		VarWatching<String> newString(String s);
	}
}
