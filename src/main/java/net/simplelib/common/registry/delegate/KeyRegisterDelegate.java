package net.simplelib.common.registry.delegate;

import api.simplelib.utils.TypeUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.common.registry.KeyBindingHandler;
import net.simplelib.common.registry.abstracts.KeyPair;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.registry.key.KeyHandler;
import api.simplelib.LoadingDelegate;
import api.simplelib.registry.key.ModKeyBinding;

/**
 * @author ci010
 */
@LoadingDelegate
public class KeyRegisterDelegate extends ASMRegistryDelegate<ModKeyBinding>
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModKeyBinding anno = this.getAnnotation();
		Class<?> clz = this.getAnnotatedClass();
		if (!KeyHandler.class.isAssignableFrom(clz))
		{
			//// TODO: 2016/1/5 Log
			return;
		}
		else
		{
			try
			{
				final KeyHandler handler = TypeUtils.cast(this.getAnnotatedClass().newInstance());
				KeyBindingHandler.add(
						new KeyPair(anno.id(), anno.keyCode())
						{
							@Override
							public void onKeyPressed()
							{
								handler.onKeyPressed(this.getKeyBinding());
							}
						});
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		KeyBindingHandler.buildList();
	}
}
