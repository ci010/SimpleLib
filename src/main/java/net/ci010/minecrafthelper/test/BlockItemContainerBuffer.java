package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.annotation.BlockItemContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
public class BlockItemContainerBuffer extends RegistryBuffer<Integer, FMLPreInitializationEvent>
{
	@Override
	protected Class<? extends Annotation> annotationType()
	{
		return BlockItemContainer.class;
	}

	@Override
	protected void parse(ASMDataTable.ASMData data)
	{
//		RegistryHelper.INSTANCE.putContainer(ASMDataUtil.getId(data), ASMDataUtil.getClass(data));
	}

	@Override
	public void invoke(FMLPreInitializationEvent state)
	{}
}
