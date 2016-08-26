package net.simplelib.network;

import api.simplelib.LoadingDelegate;
import api.simplelib.network.obj.ModMessageDispatcher;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.FinalFieldUtils;
import api.simplelib.utils.TypeUtils;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author ci010
 */
@LoadingDelegate
public class NetworkDelegate extends ASMRegistryDelegate<ModMessageDispatcher>
{
	@Mod.EventHandler
	public void constract(FMLConstructionEvent event)
	{
		if (this.getField().isPresent())
		{
			Field field = this.getField().get();
			String modid = this.getModid();
			Object obj, inst = null;
			int modifiers = field.getModifiers();
			if (!Modifier.isStatic(modifiers))
				inst = Loader.instance().getIndexedModList().get(modid).getMod();
			obj = Network.INSTANCE.getDispatcher(this.getModid(), TypeUtils.instantiateQuite(this.getAnnotation().value()));
			if (obj == null)
			{

			}
			if (Modifier.isFinal(modifiers))
				if (Modifier.isStatic(modifiers))
					try {FinalFieldUtils.INSTANCE.setStatic(field, obj);}
					catch (Exception e)
					{
						throw new IllegalArgumentException("Unexpected thing happened in network injection.", e);
					}
				else {
					try
					{
						FinalFieldUtils.INSTANCE.set(inst, field, obj);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			else
				ReflectionHelper.setPrivateValue(TypeUtils.cast(this.getAnnotatedClass()), inst, obj, field.getName());
		}
		else
			throw new IllegalArgumentException("Unexpected thing happened in network injection.");
	}
}
