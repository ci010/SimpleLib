package api.simplelib;

import java.util.ListIterator;

/**
 * This interface provide a basic structure of combination logic.
 *
 * @author ci010
 */
public interface Pipeline<T> extends Iterable<T>
{
	/**
	 * Add the element to the last.
	 *
	 * @param element the element
	 * @return this
	 */
	Pipeline<T> addLast(T element);

	/**
	 * Add the element to the first.
	 *
	 * @param element the element
	 * @return this
	 */
	Pipeline<T> addFirst(T element);

	/**
	 * This method will getResource sure the element is insertAfter the target. If not, the element will be moved insertAfter to the
	 * target.
	 * <p>If pipe doesn't contain this element, the element will be add into pipe.</p>
	 * <p>If there are multiple targets in the pipe, getResource sure to put the element insertAfter all of them.<p/>
	 *
	 * @param target  The target element.
	 * @param element The element might be moved.
	 * @return this
	 */
	Pipeline<T> addAfter(T target, T element);

	/**
	 * This method will getResource sure the element is before the target. If not, the element will be moved before to the
	 * target.
	 * <p>If pipe doesn't contain this element, the element will be add into pipe.</p>
	 * <p>If there are multiple targets in the pipe, getResource sure to put the element before all of them.<p/>
	 *
	 * @param target  The target element.
	 * @param element The element might be moved.
	 * @return this
	 */
	Pipeline<T> addBefore(T target, T element);

	/**
	 * Remove an element.
	 *
	 * @param element
	 */
	boolean remove(T element);

	/**
	 * Clear all elements.
	 */
	void clear();

	/**
	 * @return The size of this pipeline.
	 */
	int size();

	boolean contains(Object o);

	boolean isEmpty();

	/**
	 * Copy all the contents of another pipe to this pipe. This method will overlap all the current elements in this
	 * pipe with the elements in another pipe.
	 *
	 * @param pipeline Another pipe which will be copied.
	 */
	void copy(Pipeline<T> pipeline);

	T[] toArray();

	@Override
	ListIterator<T> iterator();
}
