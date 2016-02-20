package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
public interface IProperty<T extends ITagSerial>
{
	boolean shouldApply(Interactive interactive);

	void init(Interactive interactive);

	T buildProperty();
}
