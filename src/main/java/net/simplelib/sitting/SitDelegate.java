package net.simplelib.sitting;

import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.registry.ModConfig;
import api.simplelib.sitting.ModSeat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * @author ci010
 */
@LoadingDelegate
public class SitDelegate extends ASMRegistryDelegate<ModSeat>
{
	@Instance
	static SitDelegate instance = new SitDelegate();
	@ModConfig(domain = "sitting", id = "blocks", comment = "add the block getName to here to support")
	private String[] supportBlock;

	@Mod.EventHandler
	public void init(FMLPostInitializationEvent event)
	{
		if (supportBlock != null)
		{
			for (String s : supportBlock)
			{
				ResourceLocation loc = new ResourceLocation(s);
//				SitHandler.register();
			}
		}
	}
}
