package api.simplelib.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

/**
 * The AI manager of a entity.
 *
 * @author ci010
 * @see AIHandler
 */
public interface AIManager
{
	/**
	 * Remove a specific class of AI.
	 *
	 * @param clz The AI class of the AI you want to remove.
	 * @return this
	 */
	AIManager removeAI(Class<? extends EntityAIBase> clz);

	/**
	 * Add a new AI to this entity.
	 *
	 * @param priority The priority of that AI. See {@link EntityAITasks#addTask(int, EntityAIBase)}
	 * @param ai       The new AI you created.
	 * @return this
	 */
	AIManager addAI(int priority, EntityAIBase ai);

	/**
	 * Remove the specific AI in the {@link EntityAITasks#taskEntries}
	 *
	 * @param num The index number of that AI.
	 * @return this
	 */
	AIManager removeAI(int num);
}
