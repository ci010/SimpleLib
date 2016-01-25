package net.simplelib.common.registry.delegate;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.RegistryHelper;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.abstracts.NamespaceMakerSimple;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.BlockItemStruct;
import net.simplelib.common.registry.annotation.type.ModComponent;

/**
 * @author ci010
 */
@ASMDelegate
public class StaticComponentDelegate extends ASMRegistryDelegate<ModComponent>
{
	private NamespaceMakerSimple simpleMaker = new NamespaceMakerSimple().staticSensitve(false);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		String name = this.getAnnotation().name().equals("") ?
				this.getAnnotatedClass().getSimpleName() :
				this.getAnnotation().name();
		try
		{
			if (Item.class.isAssignableFrom(this.getAnnotatedClass()))
				RegistryHelper.INSTANCE.registerItem(this.getModid(), (Item) this.getAnnotatedClass().newInstance(), name);
			else if (Block.class.isAssignableFrom(this.getAnnotatedClass()))
				RegistryHelper.INSTANCE.registerBlock(this.getModid(), (Block) this.getAnnotatedClass().newInstance(), name);
			else if (this.getAnnotatedClass().isAnnotationPresent(BlockItemStruct.class))
				RegistryHelper.INSTANCE.register(this.getModid(), simpleMaker.make(this.getAnnotatedClass().newInstance()));
			else
				CommonLogger.warn("The class {} is neither a block nor an item! Moreover, it not a BlockItemStruct. " +
						"It will not be registered!", this
						.getAnnotatedClass().getName());
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
