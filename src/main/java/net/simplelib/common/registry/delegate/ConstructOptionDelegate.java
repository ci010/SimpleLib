package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.simplelib.RegistryHelper;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.ConstructOption;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.utils.GenericUtil;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
@ASMDelegate
public class ConstructOptionDelegate extends ASMRegistryDelegate<ConstructOption>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		try
		{
			RegistryHelper.INSTANCE.registerAnnotation(GenericUtil.<Annotation>cast(this.getAnnotatedClass()),
					this.getAnnotation().value().newInstance());
		}
		catch (InstantiationException e)
		{
			//TODO log that it need no parameter constructor.
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			if (this.getAnnotatedClass() == null)
				System.out.println("class null");
			if (this.getAnnotation() == null)
				System.out.println("annotaton null");
		}
	}
}
