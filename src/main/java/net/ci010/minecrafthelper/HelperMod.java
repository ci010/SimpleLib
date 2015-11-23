package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.core.CommonProxy;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.ci010.minecrafthelper.util.FMLModUtil;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

@Mod(modid = HelperMod.MODID, name = HelperMod.NAME, version = HelperMod.VERSION)
public class HelperMod
{
	public static final String MODID = "helper", NAME = "Helper", VERSION = "beta 0.2";

	@Instance(MODID)
	public static HelperMod instance;

	public static final Logger LOG = LogManager.getLogger();

	@SidedProxy(modId = MODID, serverSide = "net.ci010.minecrafthelper.core.CommonProxy", clientSide = "net.ci010.minecrafthelper.core.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
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
}
