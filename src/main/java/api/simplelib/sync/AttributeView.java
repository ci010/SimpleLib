package api.simplelib.sync;


import api.simplelib.Overview;
import com.google.common.collect.ImmutableList;
import javafx.beans.value.ChangeListener;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public interface AttributeView extends Overview<VarSync<?>>
{
	@CapabilityInject(AttributeView.class)
	Capability<AttributeView> CAPABILITY = null;

	@Nonnull
	ImmutableList<VarSync<?>> getVarsByMode(@Nonnull UpdateMode mode);
}
