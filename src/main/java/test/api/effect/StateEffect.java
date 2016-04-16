package test.api.effect;

import test.api.component.State;

/**
 * @author ci010
 */
public interface StateEffect
{
	Effect getType();

	int duration();

	int amplifier();

	int showParticles();

	void combine(StateEffect effect);

	boolean getIsAmbient();
}
