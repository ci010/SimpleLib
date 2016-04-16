package test.api.component;

import java.util.ListIterator;

/**
 * @author ci010
 */
public interface Pipeline<C extends Context> extends Iterable<Work<C>>
{
	int addLast(Work<C> work);

	int addFirst(Work<C> work);

	int addAfter(Work<C> work, int i);

	int addBefore(Work<C> work, int i);

	void removeWork(int id);

	int size();

	@Override
	ListIterator<Work<C>> iterator();
}
