package test.impl.block.module;

import test.api.component.ModuleContainer;

import java.util.ArrayList;

/**
 * @author ci010
 */
public abstract class ModuleContainerBase<T> implements ModuleContainer<T>
{
	protected ArrayList<T> list = new ArrayList<T>();

	@Override
	public ModuleContainer<T> add(T module)
	{
		list.add(module);
		return this;
	}
}
