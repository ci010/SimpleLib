package api.simplelib.sync.container;

import api.simplelib.inventory.*;
import api.simplelib.sync.AttributeView;
import api.simplelib.sync.UpdateMode;
import api.simplelib.sync.VarSync;
import api.simplelib.ui.GuiDocument;
import api.simplelib.ui.elements.Element;
import api.simplelib.ui.elements.ElementInventoryElement;
import api.simplelib.utils.DebugLogger;
import api.simplelib.vars.VarRef;
import com.google.common.collect.ImmutableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.simplelib.sync.VarSyncPrimitive;


/**
 * @author ci010
 */
public class ContainerBase extends Container
{
	private AttributeView attr;
	private ChangeListener<Object> listener = new ChangeListener<Object>()
	{
		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
		{

		}
	};

	@Override
	protected void finalize() throws Throwable
	{
		for (VarSync<?> sync : attr.getVarsByMode(UpdateMode.LAZY))
			sync.removeListener(listener);
		super.finalize();
	}

	public ContainerBase(EntityPlayer player, World world, ICapabilityProvider provider)
	{
		AttributeView attributes = provider.getCapability(AttributeView.CAPABILITY, null);
		this.attr = attributes;
		ImmutableList<VarSync<?>> varsByMode = attributes.getVarsByMode(UpdateMode.LAZY);
		for (VarSync<?> varSync : varsByMode)
			varSync.addListener(listener);
		this.loadComponent(player, world, provider);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
	}

	private void loadComponent(EntityPlayer player, World world, ICapabilityProvider provider)
	{
		GuiDocument capability = provider.getCapability(GuiDocument.CAPABILITY, null);
		Inventory inv = provider.getCapability(Inventory.CAPABILITY, null);
		for (Element element : capability.getAll())
		{
			if (loadSlots(player, inv, element)) continue;
			{

			}
		}
		//send component, syncable, inventory to client
	}


	private boolean loadSlots(EntityPlayer player, Inventory inv, Element element)
	{
		if (!(element instanceof ElementInventoryElement))
			return false;
		VarRef<String> nameRef = element.getProperties().str("slot:name");
		InventoryElement invElem;
		if (!nameRef.isPresent())
		{
			VarRef<Number> idRef = element.getProperties().num("slot:id");
			if (!idRef.isPresent())
			{
				DebugLogger.fatal("Found a Element for InventoryElement that has neither id or name! " +
						"Cannot add it to Container!");
				return false;
			}
			else invElem = inv.getById(idRef.get().intValue());
		}
		else
		{
			String name = nameRef.get();
			if (name.equals("player:inventory"))
			{
				for (int index = 0; index < 9; ++index)
					this.addSlotToContainer(new Slot(player.inventory, index,
							element.transform().x + 8 + index * 18,
							element.transform().y + 142));
				return true;
			}
			else if (name.equals("player:hotbar"))
			{
				for (int index = 0; index < 3; ++index)
					for (int offset = 0; offset < 9; ++offset)
						this.addSlotToContainer(new Slot(player.inventory, offset + index * 9 + 9,
								element.transform().x + 8 + offset * 18,
								element.transform().y + 84 + index * 18));
				return true;
			}
			invElem = inv.getByName(name);
		}
		if (invElem instanceof InventorySlot)
			this.addSlotToContainer(new SlotRuled(inv.asIInventory(), invElem.id(),
					element.transform().x, element.transform().y, invElem.getRule()));
		else if (invElem instanceof InventorySpace)
		{
			InventorySpace space = (InventorySpace) invElem;
			int xSize, ySize, size = space.getSlots(), remain = 0;
			VarRef<Number> xSizeRef = element.getProperties().num("slot:xSize");
			VarRef<Number> ySizeRef = element.getProperties().num("slot:ySize");
			if (!xSizeRef.isPresent())
				if (!ySizeRef.isPresent())
				{
					xSize = size;
					ySize = 1;
				}
				else
				{
					ySize = ySizeRef.get().intValue();
					if (ySize > size)
					{
						xSize = size;
						ySize = 1;
					}
					else
					{
						xSize = size / ySize;
						remain = size & ySize;
					}
				}
			else if (!ySizeRef.isPresent())
			{
				xSize = xSizeRef.get().intValue();
				if (xSize > size)
				{
					xSize = size;
					ySize = 1;
				}
				else
				{
					ySize = size / xSize;
					remain = size & xSize;
				}
			}
			else
			{
				xSize = xSizeRef.get().intValue();
				ySize = ySizeRef.get().intValue();
				int inspect = xSize * ySize;
				if (inspect > size)
					if (xSize > size)
					{
						xSize = size;
						ySize = 1;
					}
					else
					{
						ySize = size / xSize;
						remain = size & xSize;
					}
				else if (inspect < size)
				{
					int sub = size - inspect;
					if (sub < xSize)
						remain = sub;
					else
					{
						ySize += sub / xSize;
						remain = sub & xSize;
					}
				}
			}
			int y;
			for (y = 0; y < ySize; y++)
				for (int x = 0; x < xSize; x++)
					this.addSlotToContainer(new SlotRuled(inv.asIInventory(), invElem.id(),
							element.transform().x + x * 18, element.transform().y + y * 18,
							invElem.getRule()));
			if (remain > 0)
			{
				++y;
				for (int r = 0; r < remain; r++)
					this.addSlotToContainer(new SlotRuled(inv.asIInventory(), invElem.id(),
							element.transform().x + r * 18, element.transform().y + y * 18,
							invElem.getRule()));
			}
		}
		return true;
	}

	public ContainerBase loadPlayer(EntityPlayer player)
	{
		return loadPlayer(player, 0, 0);
	}

	public ContainerBase loadPlayer(EntityPlayer player, int startX, int startY)
	{
		int index;
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(player.inventory, index, startX + 8 + index * 18, startY + 142));
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(player.inventory, offset + index * 9 + 9, startX + 8 + offset * 18,
						startY + 84 + index * 18));
		return this;
	}


	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		if (listener instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) listener;
			AttributeView attributesHub = attr;
			if (attributesHub == null)
				return;
			ImmutableList<VarSync<?>> varsByMode = attributesHub.getVarsByMode(UpdateMode.LAZY);
			for (VarSync<?> varSync : varsByMode)
			{

			}
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
		//called when an inventory changed and this function is expected to updatable and sync the data to other players.
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

//	public static class SyncMessage extends NBTClientMessageHandler
//	{
//		public SyncMessage()
//		{
//			super();
//		}
//
//		SyncMessage(NBTTagCompound data)
//		{
//			this.delegate.set(data);
//		}
//
//		@Override
//		public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
//		{
//			NBTTagCompound real = data.getCompoundTag("data");
//			NBTTagCompound loc = data.getCompoundTag("loc");
//			ICapabilityProvider of = CapabilityProvidersSerializer.of(loc);
//			if (of == null)
//				throw new IllegalArgumentException();
//			AttributeView capability = of.getCapability(AttributeView.CAPABILITY, null);
//			for(String s : real.getKeySet())
//				capability.getByName(s).readFromNBT(real);
//			return null;
//		}
//	}
}
