package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.annotation.Construct;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.ci010.minecrafthelper.test.BlockItemContainerBuffer;
import net.ci010.minecrafthelper.test.PackageRegistryBuffer;
import net.ci010.minecrafthelper.test.RegistryBufferManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityRegistry;
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

	@SidedProxy(modId = MODID, serverSide = "net.ci010.minecrafthelper.test.RegistryBufferManager", clientSide = "net.ci010" +
			".minecrafthelper.test.RegistryBufferManagerClient")
	public static RegistryBufferManager manager;

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		manager.addCache(new BlockItemContainerBuffer());
		manager.addCache(new PackageRegistryBuffer());
		manager.invoke(event);
		proxy.loadASMDataTable(event.getASMHarvestedData());
//		ModMetadata meta = Loader.instance().activeModContainer().getMetadata();
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
		manager.invoke(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (EntityRegistryCache.EntityRegisterInfo info : EntityRegistryCache.instance())
			EntityRegistry.registerModEntity(info.clz, info.name, info.id, info.mod, info.range, info.frequnce, info.velocity);
		MinecraftForge.EVENT_BUS.register(RegistryHelper.INSTANCE.getAiRemove());
		//TODO remove this test code
		RegistryHelper.INSTANCE.registerSittableBlock(Blocks.stone_stairs);
		manager.invoke(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {manager.invoke(event);}

	@EventHandler
	public void complete(FMLLoadCompleteEvent event)
	{
		RegistryHelper.INSTANCE.close();
		manager.invoke(event);
	}

	@EventHandler
	void serverAboutStart(FMLServerAboutToStartEvent event) {manager.invoke(event);}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		ServerCommandManager serverCommandManager = (ServerCommandManager) event.getServer().getCommandManager();
		for (CommandBase cmd : CommandCache.instance())
			serverCommandManager.registerCommand(cmd);
		manager.invoke(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {manager.invoke(event);}

	@EventHandler
	void serverStopping(FMLServerStoppingEvent event) {manager.invoke(event);}

	@EventHandler
	void serverStopped(FMLServerStoppedEvent event) {manager.invoke(event);}

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
