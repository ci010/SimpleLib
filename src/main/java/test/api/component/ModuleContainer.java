package test.api.component;

/**
 * @author ci010
 */
public interface ModuleContainer<T>
{
	ModuleContainer<T> add(T module);

	T getContainer();
}
