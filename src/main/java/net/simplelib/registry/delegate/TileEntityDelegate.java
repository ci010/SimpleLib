package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.simplelib.common.CommonLogger;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.ModTileEntity;
import net.simplelib.common.utils.GenericUtil;

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
		GameRegistry.registerTileEntity(tile, name);
		CommonLogger.info("Register TileEntity {} by mod [{}]", name, this.getModid());
	}
}
