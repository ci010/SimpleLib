package net.simplelib.common;

/**
 * @author ci010
 */
public interface Maker<Input, Output>
{
	Output make(Input input);
}
