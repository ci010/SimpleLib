package net.simplelib;

import com.google.common.eventbus.EventBus;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.WorldAccessContainer;
import net.simplelib.client.loading.ExternalResource;
import net.simplelib.world.WorldPropertiesManager;

import java.util.Map;

/**
 * @author ci010
 */
public class LibModContainer extends DummyModContainer implements WorldAccessContainer
{
	public LibModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId = "lib-helper";
		meta.authorList.add("ci010");
		meta.name = "Lib-helper";
	}

	@Override
	public Object getMod()
	{
		return this;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		return true;
	}

	@Override
	public Class<?> getCustomResourcePackClass()
	{
		return ExternalResource.class;
	}

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
		WorldPropertiesManager.instance().cache = tag;
	}
}
