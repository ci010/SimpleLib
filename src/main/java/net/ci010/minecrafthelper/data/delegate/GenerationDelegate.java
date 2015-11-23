package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.RegistryHelper;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.Generate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ci010
 */
@ASMDelegate
@SideOnly(Side.CLIENT)
public class GenerationDelegate extends RegistryDelegate<Generate>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		System.out.println("GEnerate");
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
