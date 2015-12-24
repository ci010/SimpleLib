package net.simplelib.deprecated;

import net.simplelib.data.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public interface ISyncPort
{
	void provideSyncReference(List<VarSync> referenceCollection);
}
