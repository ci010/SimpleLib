package test.api.effect;

import test.api.component.entity.livingbase.StateEntityLivingBase;

/**
 * @author ci010
 */
public interface Effect //extends GameComponent<GameComponent.Builder, ContextEntity>
{
	void performance(StateEntityLivingBase entity);
}
