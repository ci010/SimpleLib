package test.api.component.entity;

import test.api.component.Context;

/**
 * @author ci010
 */
public interface ContextEntity<T extends StateEntity> extends Context
{
	@Override
	T getState();
}
