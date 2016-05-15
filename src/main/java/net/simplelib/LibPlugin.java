package net.simplelib;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.WorldAccessContainer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.simplelib.world.WorldPropertiesManager;

import java.io.File;
import java.util.Map;

/**
 * @author ci010
 */
public class LibPlugin implements IFMLLoadingPlugin
{
	private static File src;

	public static File getSource()
	{
		return src;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		src = (File) data.get("coremodLocation");
	}

	@Override
	public String getModContainerClass()
	{
		return LibModContainer.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

}
