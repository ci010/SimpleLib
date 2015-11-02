package net.ci010.minecrafthelper.abstracts;

import java.lang.annotation.Annotation;

/**
 * The interface that handle the annotation data
 *
 * @author CI010
 */
public interface ArgumentHelper
{
	/**
	 * Get all the data needed to construct from annotation.
	 *
	 * @param annotation
	 * @return All the arguments needed to construct.
	 */
	Object[] getArguments(Annotation annotation);
}
