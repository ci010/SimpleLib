package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.simplelib.HelperMod;
import net.simplelib.common.CommonLogger;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.common.ModTileEntity;
import api.simplelib.utils.GenericUtil;

/**
 * @author ci010
 */
@ASMDelegate
public class TileEntityDelegate extends ASMRegistryDelegate<ModTileEntity>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<? extends net.minecraft.tileentity.TileEntity> tile = GenericUtil.cast(this.getAnnotatedClass());
		String name = this.getAnnotation().value().equals("") ? tile.getName() : this
				.getAnnotation().value();
		ModTileEntity.Render render = this.getAnnotatedClass().getAnnotation(ModTileEntity.Render.class);
		if (HelperMod.proxy.isClient() && (render != null))
		{
			try
			{
				ClientRegistry.registerTileEntity(tile, name, render.value().newInstance());
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
		else
			GameRegistry.registerTileEntity(tile, name);

		CommonLogger.info("Register TileEntity {} by mod [{}]", name, this.getModid());
	}
}
