package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.ComponentsRepository;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.StaticComponent;
import net.ci010.minecrafthelper.data.StructBlock;
import net.ci010.minecrafthelper.data.StructItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
@ASMDelegate
public class StaticComponentDelegate extends RegistryDelegate<StaticComponent>
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		BlockItemStruct struct = null;
		try
		{
			if (Item.class.isAssignableFrom(clz))
				struct = new StructItem((Item) clz.newInstance());
			else if (Block.class.isAssignableFrom(clz))
				struct = new StructBlock((Block) clz.newInstance());
			if (struct != null)
				ComponentsRepository.put(this.getModid(), struct);
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
