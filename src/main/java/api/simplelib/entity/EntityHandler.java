package api.simplelib.entity;

import api.simplelib.registry.ModEntityHandler;
import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * The hook handle the entity's custom properties.
 * <p>You can register {@link IExtendedEntityProperties} and {@link IStatus} to entity here.<p/>
 * <p>Use {@link ModEntityHandler} to register this.</p>
 *
 * @author ci010
 * @see ModEntityHandler
 * @see IExtendedEntityProperties
 * @see IStatus
 */
public interface EntityHandler
{
	/**
	 * Handle a new entity.
	 *
	 * @param entity  The entity you want to handle.
	 * @param manager The manager which provide you ability to add custom properties to the entity.
	 */
	void handle(Entity entity, Manager manager);

	interface Manager
	{
		Manager add(String id, IExtendedEntityProperties properties);

		Manager add(String id, IStatus status);

		/**
		 * @return The AI manager of this entity. Only used for {@link net.minecraft.entity.EntityLiving}.
		 * Therefore this could be null.
		 */
		Optional<AIManager> getAIManager();
	}

	/**
	 * The AI manager of a entity.
	 *
	 * @author ci010
	 */
	interface AIManager
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
}
