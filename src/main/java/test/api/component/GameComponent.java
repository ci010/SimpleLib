package test.api.component;

import api.simplelib.common.NotNull;
import com.google.common.base.Optional;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.Capability;

/**
 * @author ci010
 */
public interface GameComponent<T extends GameComponent.Builder, C extends Context>
{
	@NotNull
	String getId();

	void build(T builder);

	void onImplementToWorld(C context);

	interface Builder
	{
		<T> Builder addModule(Module<T> moduleHook, T module);

		Builder addCapability(Capability<?> capability, NBTBase initValue);
	}
}
