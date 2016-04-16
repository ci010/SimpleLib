package test.api.component;

/**
 * @author ci010
 */
public interface Merger<T>
{
	T merge(T a, T b);
}
