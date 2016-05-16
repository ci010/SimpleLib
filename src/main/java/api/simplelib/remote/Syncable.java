package api.simplelib.remote;


import api.simplelib.vars.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public interface Syncable
{
	List<VarSync> getAllSync();
}
