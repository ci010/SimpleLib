package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.annotation.Message;
import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.ci010.minecrafthelper.util.ASMDataUtil;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
public class PackageRegistryBuffer extends RegistryBuffer<Integer, FMLInitializationEvent>
{
	@Override
	protected Class<? extends Annotation> annotationType()
	{
		return Message.class;
	}

	@Override
	protected void parse(ASMDataTable.ASMData data)
	{
		Class<IMessage> msg = (Class<IMessage>) ASMDataUtil.getClass(data);
		Class<AbstractMessageHandler<IMessage>> handler = (Class<AbstractMessageHandler<IMessage>>) ASMDataUtil.getAnnotation(data,
				Message.class).value();
//		RegistryHelper.INSTANCE.registerMessage(handler, msg);
	}

	@Override
	public void invoke(FMLInitializationEvent state)
	{}
}
