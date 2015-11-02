package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.annotation.Construct;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
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
			setActiveContainer(getModContainer(meta.modid));
			//TODO make mod class only use public method and move all other class into core package
			BlockItemRegistry.instance().process(meta);
			setActiveContainer(theMod);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(RegistryHelper.INSTANCE.getAiRemove());
		//TODO remove this test code
		RegistryHelper.INSTANCE.registerSittableBlock(Blocks.stone_stairs);
	}

	@EventHandler
	public void complete(FMLLoadCompleteEvent event)
	{
		RegistryHelper.INSTANCE.close();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		ServerCommandManager serverCommandManager = (ServerCommandManager) event.getServer().getCommandManager();
		for (CommandBase cmd : CommandCache.instance())
			serverCommandManager.registerCommand(cmd);
	}

	public ModContainer getModContainer(String modid)
	{
		return Loader.instance().getIndexedModList().get(modid);
	}

	public void setActiveContainer(ModContainer container)
	{
		ReflectionHelper.setPrivateValue(LoadController.class,
				(LoadController) ReflectionHelper.getPrivateValue(Loader.class,
						Loader.instance(),
						"modController"),
				container,
				"activeContainer");
	}
}
