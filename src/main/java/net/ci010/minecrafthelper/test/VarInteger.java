package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.container.ContainerWrap;
import net.ci010.minecrafthelper.network.MessageInteger;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
public class VarInteger extends VarSync<Integer>
{
	public VarInteger(ContainerWrap listeners)
	{
		super(listeners);
	}
}
