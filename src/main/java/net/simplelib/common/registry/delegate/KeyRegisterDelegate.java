package net.simplelib.common.registry.delegate;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.common.Local;
import net.simplelib.common.registry.KeyBindingHandler;
import net.simplelib.common.registry.KeyPair;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.abstracts.KeyHandler;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.ModKeyBinding;
import net.simplelib.common.utils.GenericUtil;

import java.util.List;

/**
 * @author ci010
 */
@ASMDelegate
public class KeyRegisterDelegate extends ASMRegistryDelegate<ModKeyBinding>
{
	private List<KeyPair> pairs = Lists.newArrayList();

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
				final KeyHandler handler = GenericUtil.cast(this.getAnnotatedClass().newInstance());
				pairs.add(new KeyPair(Local.translate("key.".concat(anno.id()).concat(".description"), anno.id()),
						anno.keyCode(),
						Local.translate("key.".concat(anno.id()).concat(".category"), anno.id()))
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
		KeyBindingHandler.buildList(pairs);
	}
}
