package api.simplelib.vars;

/**
 * @author ci010
 */
public interface VarArray<T> extends Iterable<T>
{
	/**
	 * update the index
	 *
	 * @param i
	 * @param value
	 */
	void set(int i, T value);

	T remove(int i);

	void add(T... value);

	T get(int i);

	int size();

	T[] toArray();

	void clear();
}
