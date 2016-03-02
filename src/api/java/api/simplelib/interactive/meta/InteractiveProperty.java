package api.simplelib.interactive.meta;

import api.simplelib.common.FreeConstruct;
import api.simplelib.interactive.Interactive;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
@FreeConstruct
public interface InteractiveProperty<Data extends ITagSerial, Meta,
		Interface extends InteractivePropertyHook<Data, Meta>>
{
	/**
	 * The initialization method. You should set up all the base data here.
	 * <p>Also, you need to determine if this property should apply on this interactive.</p>
	 * <p>If this return false, this property just won't load data by {@link #buildProperty()} into Minecraft.</p>
	 *
	 * @param interactive The interactive.
	 * @return Should this interactive has this property.
	 */
	boolean init(Interactive interactive);

	/**
	 * The actual build data progress.
	 * Every time a data block created in Minecraft. This method will be called.
	 *
	 * @return The data block
	 */
	Data buildProperty();

	/**
	 * @return The meta data of this property.
	 */
	Meta getMeta();

	Class<Interface> getHook();
}
