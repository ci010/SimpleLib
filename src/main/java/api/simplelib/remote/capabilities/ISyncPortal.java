package api.simplelib.remote.capabilities;

import api.simplelib.remote.Syncable;
import api.simplelib.vars.VarSyncFactory;

/**
 * @author ci010
 */
@CapabilityInjectInterface(Syncable.class)
public interface ISyncPortal
{
	/**
	 * This method provide a VarFactory that all the vars it produced are auto sync between client and server.
	 *
	 * @param varFactory
	 */
	void buildVars(VarSyncFactory varFactory);
}
