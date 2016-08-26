package net.simplelib.registry.delegate;

import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.registry.ModTileEntity;
import api.simplelib.utils.DebugLogger;
import api.simplelib.utils.TypeUtils;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ci010
 */
@LoadingDelegate
public class TileEntityDelegate extends ASMRegistryDelegate<ModTileEntity>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<? extends net.minecraft.tileentity.TileEntity> tile = TypeUtils.cast(this.getAnnotatedClass());
		String name = this.getAnnotation().value().equals("") ? tile.getSimpleName() : this
				.getAnnotation().value();
		if (FMLCommonHandler.instance().getSide().isClient())
			regRender(tile, name);
		else
			GameRegistry.registerTileEntity(tile, name);
		DebugLogger.info("Register TileEntity: [{}] <- [{}:{}]", name, this.getModid(), this.getAnnotatedClass().getName
				());
	}

	@SideOnly(Side.CLIENT)
	private void regRender(Class<? extends net.minecraft.tileentity.TileEntity> tile, String name)
	{
		ModTileEntity.Render render = this.getAnnotatedClass().getAnnotation(ModTileEntity.Render.class);
		if (render == null)
			return;
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
}
