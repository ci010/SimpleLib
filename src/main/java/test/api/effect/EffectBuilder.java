package test.api.effect;

/**
 * @author ci010
 */
public interface EffectBuilder
{
	StateEffect build();

	EffectBuilder duration(int i);

	EffectBuilder amplifier(int i);

	EffectBuilder isAmbient(boolean b);
}
