package api.simplelib.ai;

import net.minecraft.entity.EntityLiving;

/**
 * The AI handler handles the addition or remove an AI.
 * <p>The class implementing this interface and annotated by {@link ModAI} will be register.<p/>
 *
 * @author ci010
 * @see ModAI
 * @see AIManager
 */
public interface AIHandler<E extends EntityLiving>
{
	/**
	 * You use {@link AIManager} to add or remove a AI from this entity.
	 *
	 * @param entity  The entity you want to modify its AI.
	 * @param manager The AI manager of that entity.
	 */
	void handleAI(E entity, AIManager manager);
}
