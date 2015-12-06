package net.ci010.minecrafthelper.machine;

import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.ModMachine;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author ci010
 */
@ASMDelegate
public class MachineDelegate extends RegistryDelegate<ModMachine>
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if (!MachineInfo.class.isAssignableFrom(this.getAnnotatedClass()))
			throw new IllegalArgumentException("ModMachine annotation should annotate a class extended MachineInfo");
		try
		{
			MachineInfo info = (MachineInfo) this.getAnnotatedClass().newInstance();
			info.modid = this.getModid();
			info.name = this.getAnnotatedClass().getSimpleName();
			new Machine(info);
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
