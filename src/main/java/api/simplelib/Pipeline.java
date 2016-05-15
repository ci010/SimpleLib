package api.simplelib;

import java.util.ListIterator;

/**
 * This interface provide a basic structure of combination logic.
 *
 * @author ci010
 */
public interface Pipeline<T> extends Iterable<T>
{
	Pipeline<T> addLast(T element);

	Pipeline<T> addFirst(T element);

	Pipeline<T> addAfter(T target, T element);

	Pipeline<T> addBefore(T target, T element);

	void remove(T element);

	void clear();

	int size();

	@Override
	ListIterator<T> iterator();
}
