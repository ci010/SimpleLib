package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.annotation.Construct;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

@Mod(modid = HelperMod.MODID, name = HelperMod.NAME, version = HelperMod.VERSION)
public class HelperMod
{
	public static final String MODID = "helper", NAME = "Helper", VERSION = "beta 0.2";

	@Instance(MODID)
	public static HelperMod instance;

	public static final Logger LOG = LogManager.getLogger();

	@SidedProxy(modId = MODID, serverSide = "net.ci010.minecrafthelper.CommonProxy", clientSide = "net.ci010.minecrafthelper.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		proxy.loadASMDataTable(event.getASMHarvestedData());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		List<ModContainer> lst = Loader.instance().getActiveModList();
		ModContainer theMod = Loader.instance().activeModContainer();
		if (lst.indexOf(theMod) != lst.size() - 1)
		{
			lst.remove(theMod);
			lst.add(theMod);
			return;
		}
		RegistryHelper.INSTANCE.registerAnnotation(Construct.Float.class, new Construct.FloatHelper());
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			setActiveContainer(getContainer(meta.modid));
			BlockItemRegistry.instance().process(meta);
			setActiveContainer(theMod);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(RegistryHelper.INSTANCE.getAiRemove());
	}

	@EventHandler
	public void complete(FMLLoadCompleteEvent event)
	{
		RegistryHelper.INSTANCE.close();
	}

	ModContainer getContainer(String modid)
	{
		return Loader.instance().getIndexedModList().get(modid);
	}

	void setActiveContainer(ModContainer container)
	{
		ReflectionHelper.setPrivateValue(LoadController.class,
				(LoadController) ReflectionHelper.getPrivateValue(Loader.class,
						Loader.instance(),
						"modController"),
				container,
				"activeContainer");
	}
}
