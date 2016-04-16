package test.api.component;

import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
public interface Work<C extends Context> extends ITagSerializable
{
	void init(C context);

	void work();

	boolean isDone();
}
