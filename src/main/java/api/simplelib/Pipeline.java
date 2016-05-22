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
	 * This method will make sure the element is after the target. If not, the element will be moved after to the
	 * target.
	 * <p>If there is no this element, the element will be add into pipe.</p>
	 *
	 * @param target  The target element.
	 * @param element The element might be moved.
	 * @return this
	 */
	Pipeline<T> setAfter(T target, T element);

	/**
	 * This method will make sure the element is before the target. If not, the element will be moved before to the
	 * target.
	 * <p>If there is no this element, the element will be add into pipe.</p>
	 *
	 * @param target  The target element.
	 * @param element The element might be moved.
	 * @return this
	 */
	Pipeline<T> setBefore(T target, T element);

	/**
	 * Remove an element.
	 *
	 * @param element
	 */
	void remove(T element);

	/**
	 * Clear all elements.
	 */
	void clear();

	/**
	 * @return The size of this pipeline.
	 */
	int size();

	/**
	 * Copy all the contents of another pipe to this pipe. This method will overlap all the current elements in this
	 * pipe with the elements in another pipe.
	 *
	 * @param pipeline Another pipe which will be copied.
	 */
	void copy(Pipeline<T> pipeline);

	@Override
	ListIterator<T> iterator();
}
