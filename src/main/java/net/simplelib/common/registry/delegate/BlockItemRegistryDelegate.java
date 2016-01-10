package net.simplelib.common.registry.delegate;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.HelperMod;
import net.simplelib.RegistryHelper;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.ContainerMeta;
import net.simplelib.common.registry.FileReference;
import net.simplelib.common.registry.LanguageReporter;
import net.simplelib.common.registry.Namespace;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.abstracts.NamespaceMakerComplex;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.BlockItemContainer;
import net.simplelib.common.utils.NameFormattor;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author ci010
 */
@ASMDelegate
public class BlockItemRegistryDelegate extends ASMRegistryDelegate<BlockItemContainer>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		NamespaceMakerComplex maker = new NamespaceMakerComplex(RegistryHelper.INSTANCE.getAnnotationMap());

		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			CommonLogger.info("Start to register [".concat(meta.modid).concat("] mod."));
			ImmutableSet<Namespace> cache;
			for (Class c : meta.getRawContainer())
				if ((cache = maker.make(c)) != null)
					meta.addUnregistered(cache);
			RegistryHelper.INSTANCE.end();
		}
	}

	private void register(ContainerMeta meta)
	{
		String registerName;
		String unlocalizedName;
		for (Namespace namespace : meta.getUnregistered())
		{
			registerName = NameFormattor.upperTo_(namespace.toString());
			unlocalizedName = NameFormattor._toPoint(registerName);
			namespace.getComponent().setUnlocalizedName(unlocalizedName);
			namespace.getComponent().register(registerName);
			if (namespace.needRegOre())
				namespace.getComponent().registerOre(namespace.getOreName());
		}
	}

	@SideOnly(Side.CLIENT)
	private void registerClient(ContainerMeta meta)
	{
		for (Namespace namespace : meta.getUnregistered())
			if (meta.getModelHandler() == null || !meta.getModelHandler().handle(namespace.getComponent()))
				namespace.getComponent().registerModel(NameFormattor.upperTo_(namespace.toString()));
		if (HelperMod.DEBUG_MOD)
		{
			FileReference.registerFile(meta.modid);
			LanguageReporter.instance().setLangType(meta.modid, meta.langType());
			for (Namespace namespace : meta.getUnregistered())
				LanguageReporter.instance().report(namespace.getComponent().getUnlocalizedName());
			try
			{
				LanguageReporter.instance().writeLang();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			this.register(meta);
			if (HelperMod.proxy.isClient())
				this.registerClient(meta);
			RegistryHelper.INSTANCE.end();
		}
	}
}
