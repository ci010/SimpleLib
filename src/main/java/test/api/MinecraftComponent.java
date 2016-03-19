package test.api;

import api.simplelib.common.NotNull;
import api.simplelib.common.Nullable;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author ci010
 */
public interface MinecraftComponent
{
	@NotNull
	String getId();

	@Nullable
	CreativeTabs getCreativeTabs();
}
