package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.ModTileEntity;
import net.simplelib.util.GenericUtil;

/**
 * @author ci010
 */
@ASMDelegate
public class TileEntityDelegate extends RegistryDelegate<ModTileEntity>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<? extends net.minecraft.tileentity.TileEntity> tile = GenericUtil.cast(this.getAnnotatedClass());
		GameRegistry.registerTileEntity(tile, this.getAnnotation().value().equals("") ? tile.getName() : this
				.getAnnotation().value());
	}
}
