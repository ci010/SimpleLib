package net.ci010.minecrafthelper.test;

import net.minecraftforge.fml.common.event.*;

/**
 * @author ci010
 */
public interface ModProxy
{
	void construct(FMLConstructionEvent event);

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void complete(FMLLoadCompleteEvent event);

	void serverAboutStart(FMLServerAboutToStartEvent event);

	void serverStarting(FMLServerStartingEvent event);

	void serverStarted(FMLServerStartedEvent event);

	void serverStopping(FMLServerStoppingEvent event);

	void serverStopped(FMLServerStoppedEvent event);
}
