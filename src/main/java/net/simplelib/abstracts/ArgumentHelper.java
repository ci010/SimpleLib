package net.simplelib.abstracts;

import net.simplelib.registry.ReflectionMaker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * The interface that handle the annotation data.
 * <p/>
 * See {@link ReflectionMaker#make(Field)}
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
