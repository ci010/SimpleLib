package net.simplelib.client;

import api.simplelib.utils.Local;
import api.simplelib.utils.Environment;
import net.minecraftforge.fml.relauncher.Side;
import net.simplelib.CommonProxy;
import net.simplelib.common.registry.ContainerMeta;
import api.simplelib.utils.FileReference;
import net.simplelib.common.registry.LanguageReporter;
import net.simplelib.common.registry.Namespace;
import api.simplelib.utils.NameFormattor;

public class ClientProxy extends CommonProxy
{
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}

	@Override
	protected void register(ContainerMeta meta)
	{
		super.register(meta);
		for (Namespace namespace : meta.getUnregistered())
			if (meta.getModelHandler() == null || !meta.getModelHandler().handle(namespace.getComponent()))
				namespace.getComponent().registerModel(NameFormattor.upperTo_(namespace.toString()));
		if (Environment.debug())
		{
			FileReference.registerFile("all");
			FileReference.registerFile(meta.modid);
			LanguageReporter.instance().start(meta.modid, meta.langType());
			for (Namespace namespace : meta.getUnregistered())
				Local.trans(namespace.getComponent().getUnlocalizedName());
			LanguageReporter.instance().end();
		}
	}

	@Override
	public boolean isClient()
	{
		return true;
	}
}
