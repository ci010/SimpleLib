package api.simplelib.interactive.meta;

import api.simplelib.Context;
import api.simplelib.common.FreeConstruct;
import api.simplelib.common.NotNull;
import api.simplelib.interactive.Interactive;
import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
@FreeConstruct
public interface InteractiveProperty
{
	interface Worker
	{
		/**
		 * The initialization method. You should set up all the base data here.
		 * <p>Also, you need to determine if this property should apply on this interactive.</p>
		 * <p>If this return false, this property just won't load data by {@link #buildProperty(Context)} into Minecraft
		 * .</p>
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
		ITagSerializable buildProperty(Context context);
	}

	@NotNull
	Worker newWorker();

	/**
	 * Build The meta data of this property.
	 */
	void build();

	Class<? extends Interactive> interfaceType();
}
