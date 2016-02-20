package net.simplelib.interactive.machine;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.HelperMod;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class MachineDelegate extends ASMRegistryDelegate<ModMachine>
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if (!MachineInfo.class.isAssignableFrom(this.getAnnotatedClass()))
			throw new IllegalArgumentException("ModMachine annotation should annotate a class extended MachineInfo");
		try
		{
			MachineInfo info = (MachineInfo) this.getAnnotatedClass().newInstance();
//			InteractiveMetadata metaData = info.getMetaData();
//			if (metaData == null)
//			{
//
//			}
		}
		catch (InstantiationException e)
		{
			HelperMod.LOG.fatal("The class {} should have a constructor without any argument. This machine will not " +
					"be registered.", this.getAnnotatedClass());
		}
		catch (IllegalAccessException e)
		{
			HelperMod.LOG.fatal("The class {} should be assessable. This machine will not be registered.", this.getAnnotatedClass());
		}
	}
}
