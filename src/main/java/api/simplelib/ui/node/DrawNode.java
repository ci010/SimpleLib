package api.simplelib.ui.node;

import api.simplelib.ui.elements.Element;
import com.google.common.base.Predicate;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public interface DrawNode extends Predicate<Element>
{
	/**
	 * Render the element. Return value decides if this add present next loop. If true, this add will be
	 * removed next iteration. If false, the add will not be removed.
	 *
	 * @param component The element.
	 * @return If next iteration will need this add for the element.
	 */
	@Override
	boolean apply(@Nonnull Element component);
}
