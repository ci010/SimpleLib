package api.simplelib.entity;

import com.google.common.collect.ImmutableList;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
public interface IStatus extends ITagSerial
{
	void build(ImmutableList.Builder<VarWatching> builder, VarWatching.Factory factory);

	VarWatching get(int index);
}
