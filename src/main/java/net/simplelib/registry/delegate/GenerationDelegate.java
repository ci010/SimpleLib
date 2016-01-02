package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.RegistryHelper;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.Generate;

/**
 * @author ci010
 */
@ASMDelegate
@SideOnly(Side.CLIENT)
public class GenerationDelegate extends ASMRegistryDelegate<Generate>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		Generate anno = this.getAnnotation();
		Generate.GenerateType[] types = anno.value();
		int length = types.length;
		switch (length)
		{
			case 0:
				//TODO log
				break;
			case 2:
				RegistryHelper.INSTANCE.setLang(this.getModid(), anno.supportLang());
				RegistryHelper.INSTANCE.setModel(this.getModid());
				break;
			case 1:
				if (types[0] == Generate.GenerateType.model)
					RegistryHelper.INSTANCE.setModel(this.getModid());
				else
					RegistryHelper.INSTANCE.setLang(this.getModid(), anno.supportLang());
				break;
			default:
				//TODO warn
				break;
		}
	}
}
