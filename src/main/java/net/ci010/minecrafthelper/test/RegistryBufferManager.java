package net.ci010.minecrafthelper.test;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

/**
 * @author ci010
 */
public class RegistryBufferManager extends RegistryBuffer<RegistryBuffer, FMLStateEvent>
{
	@Override
	protected Class<? extends Annotation> annotationType()
	{
		return null;
	}

	@Override
	protected void parse(ASMDataTable.ASMData data)
	{}

	@Override
	public void invoke(FMLStateEvent state)
	{
		Class<? extends FMLStateEvent> clz = state.getClass();
		for (RegistryBuffer buff : this.cache)
			if (clz == FMLConstructionEvent.class)
				for (ASMDataTable.ASMData data : ((FMLConstructionEvent) state).getASMHarvestedData().getAll(buff.annotationType().getName
						()))
					buff.parse(data);
			else
			{
				if (((ParameterizedType) buff.getClass().getGenericSuperclass())
						.getActualTypeArguments()
						[1] ==
						clz)
					buff.invoke(state);
			}
	}
}
