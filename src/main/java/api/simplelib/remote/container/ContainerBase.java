package api.simplelib.remote.container;

import api.simplelib.Callback;
import api.simplelib.gui.ComponentProvider;
import api.simplelib.gui.Properties;
import api.simplelib.inventory.Inventory;
import api.simplelib.network.ModNetwork;
import api.simplelib.remote.Syncable;
import api.simplelib.remote.capabilities.CapabilitiesCommon;
import api.simplelib.vars.VarForward;
import api.simplelib.vars.VarSync;
import api.simplelib.gui.components.GuiComponent;
import api.simplelib.inventory.InventoryElement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.HelperMod;
import net.simplelib.inventory.SlotRuled;
import net.simplelib.network.SyncMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public class ContainerBase extends Container implements Callback<VarSync>//, SyncHub.Portal
{
	private ImmutableList<? extends VarSync> syncs;
	private ImmutableList<GuiComponent> components;
	private String ownerId;

	public ContainerBase load(World world, ICapabilityProvider provider)
	{
		Inventory inv = provider.getCapability(CapabilitiesCommon.INVENTORY, null);
		if (inv != null)
			for (InventoryElement element : inv)
				this.loadSlots(SlotRuled.of(element));
		Syncable syncable = provider.getCapability(CapabilitiesCommon.SYNC, null);
		if (syncable != null)
			this.load(syncable.getAllSync());
		ComponentProvider componentProvider = provider.getCapability(CapabilitiesCommon.COMPONENTS, null);
		ArrayList<GuiComponent> list = Lists.newArrayList();
		componentProvider.provideComponents(list);
		components = ImmutableList.copyOf(list);
		ownerId = provider.getClass().getSimpleName();
		if (!world.isRemote)
		{
			for (GuiComponent component : components)
				for (Properties.Key key : component.getProperties().allProperties())
				{
					VarForward prop = component.getProperties().property(key);
					Object v = prop.get();
					if (v instanceof Object[])
						for (Object o : (Object[]) v)
							revolveConnection(o);
					else if (v instanceof Iterable)
						for (Object o : ((Iterable) v))
							revolveConnection(o);
					else revolveConnection(prop.delegate());
				}
			//send component, syncable, inventory to client
		}
		return this;
	}

	private void revolveConnection(Object o)
	{
		if (!(o instanceof VarSync))
			return;
		int i = syncs.indexOf(o);
		if (i != -1)
		{
			//TODO link the resource
		}
	}

	public ContainerBase loadPlayer(EntityPlayer player)
	{
		return loadPlayer(player, 0, 0);
	}

	public ContainerBase loadPlayer(EntityPlayer player, int startX, int startY)
	{
		if (player == null)
			player = HelperMod.proxy.getPlayer();
		int index;
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(player.inventory, index, startX + 8 + index * 18, startY + 142));
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(player.inventory, offset + index * 9 + 9, startX + 8 + offset * 18,
						startY + 84 + index * 18));
		return this;
	}


	private ContainerBase loadSlots(List<? extends Slot> slots)
	{
		for (Slot slot : slots)
			this.addSlotToContainer(slot);
		return this;
	}

	private ContainerBase load(List<VarSync> syncs)
	{
		this.syncs = ImmutableList.copyOf(syncs);
		for (VarSync sync : syncs)
			sync.add(this);
		return this;
	}


	public List<EntityPlayerMP> getPlayers()
	{
		List<EntityPlayerMP> players = Lists.newArrayList();
		for (Object o : this.crafters)
			if (o instanceof EntityPlayerMP)
				players.add((EntityPlayerMP) o);
		return players;
	}

	@Override
	public void onCraftGuiOpened(ICrafting iCrafting)
	{
		if (iCrafting instanceof EntityPlayerMP)
		{
			//TODO send gui component msg
			EntityPlayerMP playerMP = (EntityPlayerMP) iCrafting;
			for (int num = 0; num < syncs.size(); ++num)
				ModNetwork.instance().sendTo(new SyncMessage(this.windowId, num, syncs.get(num)),
						playerMP);
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		super.onCraftMatrixChanged(inventoryIn);
		//TODO think about this...
		//this basically is a callback function...
		//called when an inventory changed and this function is expected to update and sync the data to other players.
		//The inventory seems like always be the temporally inventory.
//		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	//	@Override
	public void onInventoryChange(IInventory inventoryIn)
	{
		onCraftMatrixChanged(inventoryIn);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return false;
	}

	@Override
	public void onChange(VarSync varSync)
	{
		if (syncs.contains(varSync))
		{
			int i = syncs.indexOf(varSync);
			for (EntityPlayerMP playerMP : getPlayers())
				ModNetwork.instance().sendTo(new SyncMessage(this.windowId, i, varSync),
						playerMP);
		}
	}

	@SideOnly(Side.CLIENT)
	public void load(int id, NBTTagCompound tag)
	{
		syncs.get(id).readFromNBT(tag);
	}

	@SideOnly(Side.CLIENT)
	public void load(Collection<GuiComponent> components)
	{

	}
}
