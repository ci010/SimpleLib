package net.simplelib.deprecated;

import net.simplelib.common.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public interface ISyncPort
{
	void provideSyncReference(List<VarSync> referenceCollection);
}
