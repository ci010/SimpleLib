package test.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.GameComponent;
import test.api.component.Module;
import test.api.component.item.ComponentItem;

/**
 * @author ci010
 */
public class ItemBuilder implements ComponentItem.Builder
{
	@Override
	public <T> GameComponent.Builder addModule(Module<T> moduleHook, T module)
	{
		return null;
	}

	@Override
	public GameComponent.Builder addCapability(Capability<?> capability, NBTBase initValue)
	{
		return null;
	}


}
