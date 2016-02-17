package net.simplelib.world;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.WorldAccessContainer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author ci010
 */
public class WorldPlugin implements IFMLLoadingPlugin, WorldAccessContainer
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}

	@Override
	public String getModContainerClass()
	{
		return null;
//		return ModContainer.class.getName();
	}

//	public static class ModContainer extends FMLModContainer
//	{
//		public ModContainer()
//		{
//			super("ModContainer", null, null);
//		}
//	}

	@Override
	public NBTTagCompound getDataForWriting(SaveHandler handler, WorldInfo info)
	{
		NBTTagCompound compound = new NBTTagCompound();
		WorldPropertiesManager.instance().writeToNBT(compound);
		return compound;
	}

	@Override
	public void readData(SaveHandler handler, WorldInfo info, Map<String, NBTBase> propertyMap, NBTTagCompound tag)
	{
		WorldPropertiesManager.instance().readFromNBT(tag);
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
