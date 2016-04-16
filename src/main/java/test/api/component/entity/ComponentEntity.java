package test.api.component.entity;

import test.api.component.GameComponent;
import api.simplelib.utils.ITagSerializer;

/**
 * @author ci010
 */

public interface ComponentEntity extends GameComponent<ComponentEntity.Builder, ContextEntity>
{
	int getHeight();

	int getWidth();

	interface Builder extends GameComponent.Builder
	{
		<T> void applyAttribute(IAttribute<T> attribute, ITagSerializer<T> serializer);
	}
}
