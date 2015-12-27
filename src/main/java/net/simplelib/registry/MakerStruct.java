package net.simplelib.registry;

import com.google.common.collect.ImmutableSet;
import net.simplelib.abstracts.ArgumentHelper;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class MakerStruct<In> extends ReflectionMaker<In, ImmutableSet<Namespace>>
{
	public MakerStruct(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		super(map);
	}
}
