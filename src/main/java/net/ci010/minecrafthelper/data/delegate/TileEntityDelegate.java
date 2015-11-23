package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.ModTileEntity;
import net.ci010.minecrafthelper.util.GenericUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ci010
 */
@ASMDelegate
public class TileEntityDelegate extends RegistryDelegate<ModTileEntity>
{
	public void init(FMLInitializationEvent event)
	{
		Class<? extends net.minecraft.tileentity.TileEntity> tile = GenericUtil.cast(this.getAnnotatedClass());
		GameRegistry.registerTileEntity(tile, this.getAnnotation().value().equals("") ? tile.getName() : this
				.getAnnotation().value());
	}
}
