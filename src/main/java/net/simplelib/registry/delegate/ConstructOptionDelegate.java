package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.simplelib.RegistryHelper;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.ConstructOption;
import net.simplelib.annotation.type.ASMDelegate;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
@ASMDelegate
public class ConstructOptionDelegate extends RegistryDelegate<ConstructOption>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		try
		{
			RegistryHelper.INSTANCE.registerAnnotation((Class<? extends Annotation>) this.getAnnotatedClass(), this.getAnnotation().value()
					.newInstance());
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
