package net.ci010.minecrafthelper.core;

import net.ci010.minecrafthelper.abstracts.ArgumentHelper;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import scala.Int;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class MakerStruct<In> extends Maker<In, BlockItemStruct>
{
	public MakerStruct(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		super(map);
	}
}
