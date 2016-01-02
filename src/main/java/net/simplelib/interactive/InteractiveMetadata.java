package net.simplelib.interactive;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.HelperMod;
import net.simplelib.common.VarSync;
import net.simplelib.gui.GuiContainerCommon;
import net.simplelib.network.GuiHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class InteractiveMetadata implements GuiHandler.ContainerProvider
{
	private static Map<String, InteractiveMetadata> registerMap = Maps.newHashMap();

	public static InteractiveMetadata getMetaData(String id)
	{
		return registerMap.get(id);
	}

	protected String id;
	protected final int guiID;
	protected ProcessHandler handler;
	protected InventoryManager slots;
	protected Interactive provider;

	public InteractiveMetadata(Interactive provider)
	{
		this.provider = provider;
		this.guiID = GuiHandler.addContainerProvider(this);
		handler = new ProcessHandler(provider);
		if (!handler.available())
			handler = null;
		slots = new InventoryManager();
		provider.provideInventory(slots);
		if (slots.isEmpty())
			slots = null;
		this.id = provider.getId();
		if (registerMap.containsKey(id))
		{
			HelperMod.LOG.fatal("The duplicated id!");//TODO finish this
			throw new IllegalArgumentException("The duplicated id!");
		}
		registerMap.put(this.id, this);
	}

	public InteractiveEntity createEntity(World world)
	{
		InteractiveEntity entity = null;
		if (this.slots != null)
		{
			ImmutableMap<String, Inventory> inventories = slots.apply();
			if (this.handler != null)
			{
				List<Process> processes = handler.buildProcess();
				List<VarInteger> ints = new ArrayList<VarInteger>(handler.numOfInt);
				List<VarSync> nbts = new ArrayList<VarSync>(handler.numOfSync);
				try
				{
					for (int i = 0; i < processes.size(); ++i)
					{
						Process p = processes.get(i);
						ProcessHandler.Info info = handler.infoList.get(i);
						for (Field field : info.integers)
						{
							VarInteger temp = (VarInteger) field.get(p);
							if (temp == null)
								field.set(p, temp = new VarInteger(0));
							ints.add(temp);
						}
						for (Field field : info.sync)
						{
							VarSync temp = (VarSync) field.get(p);
							if (temp == null)
								field.set(p, temp = new VarSync());
							nbts.add(temp);
						}
						for (ProcessHandler.HolderInfo holderInfo : info.holderInfos)
						{
							Inventory inv;
							VarItemHolder holder = (VarItemHolder) holderInfo.field.get(p);
							if (holder == null)
								holderInfo.field.set(p, holder = new VarItemHolder());
							if (holderInfo.owner == null)
								inv = inventories.get("default");
							else
								inv = inventories.get(holderInfo.owner);
							if (inv.holders == null)
								inv.holders = new ArrayList<VarItemHolder>(handler.numOfStack);
							inv.assignNamespace(holderInfo.name, holder);
						}
					}
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				entity = new InteractiveEntityUpdate("interactive_".concat(this.id), world).loadProcess(processes, ints,
						nbts);
			}
			if (entity == null)
				entity = new InteractiveEntity("interactive_".concat(this.id), world);
			entity.loadInventory(inventories);
		}
		return entity;
	}

	public void interactWith(EntityPlayer player, BlockPos pos)
	{
		player.openGui(HelperMod.instance, this.guiID, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public GuiContainer getGuiContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiContainerCommon(getContainer(player, world, x, y, z), provider);
	}
}
