package net.simplelib.entity;

import api.simplelib.remote.capabilities.ISyncPortal;
import api.simplelib.inventory.Inventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.inventory.InvImpl;

import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class CapabilityInterfaceInject
{
	//	private ArrayList<SyncPortal<?>> entities = Lists.newArrayList();
//	private ArrayList<SyncPortal<?>> tileEntities = Lists.newArrayList();
	private static ResourceLocation location = new ResourceLocation("helper", "delegate");

	static
	{
		CapabilityManager.INSTANCE.register(Inventory.class, new Capability.IStorage<Inventory>()
		{
			@Override
			public NBTBase writeNBT(Capability<Inventory> capability, Inventory instance, EnumFacing side)
			{
				return null;
			}

			@Override
			public void readNBT(Capability<Inventory> capability, Inventory instance, EnumFacing side, NBTBase nbt)
			{

			}
		}, new Callable<Inventory>()
		{
			@Override
			public Inventory call() throws Exception
			{
				return new InvImpl();
			}
		});
	}

	//	public void registerTileEntity(SyncPortal<TileEntity, ?> portal)
//	{
//		tileEntities.add(portal);
//	}
//
//	public <T> void registerEntity(SyncPortal<Entity, T> portal)
//	{
//		entities.add(portal);
//	}
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttachCapEntityStart(AttachCapabilitiesEvent.Entity event)
	{
		event.addCapability(location, new CapabilityDelegate());
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAttachCapEntity(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof ISyncPortal)
		{
			ISyncPortal syncPortal = (ISyncPortal) event.getEntity();
			EntityVarFactory varFactory = new EntityVarFactory(event.getEntity());
			syncPortal.buildVars(varFactory);
			varFactory.getAllTracking();
		}
		for (ICapabilityProvider provider : event.getCapabilities().values())
			if (provider instanceof ISyncPortal)
			{
				ISyncPortal syncPortal = (ISyncPortal) provider;
				EntityVarFactory varFactory = new EntityVarFactory(event.getEntity());
				syncPortal.buildVars(varFactory);
			}
	}

	@SubscribeEvent
	public void onAttachCapTileEntity(AttachCapabilitiesEvent.TileEntity event)
	{
		if (event.getTileEntity() instanceof ISyncPortal)
		{
			ISyncPortal syncPortal = (ISyncPortal) event.getTileEntity();
			TileEntityVarFactory factory = new TileEntityVarFactory();
			syncPortal.buildVars(factory);
		}
		for (ICapabilityProvider provider : event.getCapabilities().values())
			if (provider instanceof ISyncPortal)
			{
				ISyncPortal syncPortal = (ISyncPortal) provider;
				TileEntityVarFactory factory = new TileEntityVarFactory();
				syncPortal.buildVars(factory);
			}
	}
}
