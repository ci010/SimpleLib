package net.ci010.minecrafthelper.deprecated;

import net.ci010.minecrafthelper.data.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public interface ISyncPort
{
	void provideSyncReference(List<VarSync> referenceCollection);
}
