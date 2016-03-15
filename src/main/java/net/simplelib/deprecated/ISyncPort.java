package net.simplelib.deprecated;

import api.simplelib.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public interface ISyncPort
{
	void provideSyncReference(List<VarSync> referenceCollection);
}
