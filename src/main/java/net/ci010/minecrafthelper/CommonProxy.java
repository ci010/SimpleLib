package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.abstracts.ASMDataParser;
import net.ci010.minecrafthelper.annotation.Message;
import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public void loadASMDataTable(ASMDataTable table)
	{
		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				RegistryHelper.INSTANCE.putContainer(this.getId(data), this.getClass(data));
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.BlockItemContainer"));

		new ASMDataParser()
		{
			@SuppressWarnings("unchecked")
			@Override
			protected void parse(ASMData data)
			{
				Class<IMessage> msg = (Class<IMessage>) this.getClass(data);
				Class<AbstractMessageHandler<IMessage>> handler = (Class<AbstractMessageHandler<IMessage>>) this.getAnnotation(data,
						Message.class).value();
				RegistryHelper.INSTANCE.registerMessage(handler, msg);
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.Message"));
	}

	public boolean isClient() {return false;}

	public boolean isClientSide() {return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;}
}
