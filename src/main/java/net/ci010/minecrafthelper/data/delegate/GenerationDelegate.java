package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.RegistryHelper;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.GenLang;
import net.ci010.minecrafthelper.annotation.type.GenModel;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ci010
 */
@ASMDelegate
@SideOnly(Side.CLIENT)
public class GenerationDelegate extends RegistryDelegate<Mod>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (clz.isAnnotationPresent(GenLang.class))
			RegistryHelper.INSTANCE.setLang(this.getModid(), clz.getAnnotation(GenLang.class).value());
		if (clz.isAnnotationPresent(GenModel.class))
			RegistryHelper.INSTANCE.setModel(this.getModid());
	}
}
