package net.simplelib.registry;

import net.simplelib.abstracts.ArgumentHelper;
import net.simplelib.abstracts.BlockItemStruct;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class MakerStruct<In> extends ReflectionMaker<In, BlockItemStruct>
{
	public MakerStruct(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		super(map);
	}
}
