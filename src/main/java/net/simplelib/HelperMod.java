package net.simplelib;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = HelperMod.MODID, name = HelperMod.NAME, version = HelperMod.VERSION, useMetadata = true)
public class HelperMod
{
	public static final String MODID = "helper", NAME = "Helper", VERSION = "beta 0.2";

	public static final boolean DEBUG_MOD;

	@Instance(MODID)
	public static HelperMod instance;

	public static final Logger LOG = LogManager.getLogger();

	@SidedProxy(modId = MODID, serverSide = "net.simplelib.CommonProxy", clientSide = "net.simplelib.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		CommonLogger.init();
		if (DEBUG_MOD)
			CommonLogger.info("Detected that this is a development environment. Debug mode on.");
		RegistryHelper.INSTANCE.container = Loader.instance().activeModContainer();
		RegistryBufferManager.instance().load(event.getASMHarvestedData());
		RegistryBufferManager.instance().invoke(event);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RegistryBufferManager.instance().invoke(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//TODO remove this test code
		ModMetadata meta = Loader.instance().activeModContainer().getMetadata();
		meta.description = "A mod that can simplify the mod registrant.";
		meta.url = "https://github.com/ci010/SimpleLib";
		meta.authorList.add(0, "ci010");
		meta.updateUrl = "https://github.com/ci010/SimpleLib/releases";
		RegistryHelper.INSTANCE.registerSittableBlock(Blocks.stone_stairs);
		RegistryBufferManager.instance().invoke(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {RegistryBufferManager.instance().invoke(event);}

	@EventHandler
	public void complete(FMLLoadCompleteEvent event)
	{
		RegistryBufferManager.instance().invoke(event);
		RegistryHelper.INSTANCE.close();
	}

	@EventHandler
	void serverAboutStart(FMLServerAboutToStartEvent event) {RegistryBufferManager.instance().invoke(event);}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		RegistryBufferManager.instance().invoke(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		RegistryBufferManager.instance().invoke(event);
		RegistryBufferManager.close();
	}

	static
	{
		boolean fail = false;
		try
		{
			Class.forName("net.minecraftforge.gradle.GradleStartCommon");
		}
		catch (ClassNotFoundException e)
		{
			fail = true;
		}
		DEBUG_MOD = !fail;
	}
}
