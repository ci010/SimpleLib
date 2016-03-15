package api.simplelib.container;

import api.simplelib.Var;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.inventory.SpaceInfo;
import api.simplelib.interactive.process.ProcessPipeline;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import api.simplelib.gui.GuiContainerCommon;
import api.simplelib.gui.GuiProvider;
import net.simplelib.interactive.InteractiveEntity;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.TileEntityDummy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author ci010
 */
public class ContainerProvider implements IContainerProvider
{
	private GuiProvider provider;
	private boolean hasInv, hasProc;
	private String id;

	public ContainerProvider(Interactive interactive, GuiProvider provider)
	{
		this.provider = provider;
		this.id = interactive.getId();
		hasInv = interactive instanceof Inventory;
		hasProc = interactive instanceof ProcessPipeline;
	}

	@Override
	public Container getContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		final BlockPos pos = new BlockPos(x, y, z);
		final TileEntity tileEntity = world.getTileEntity(pos);
		ContainerCommon container = new ContainerCommon();
		InteractiveMetadata metaData = InteractiveMetadata.getInstance(this.id);

		if (!(tileEntity instanceof TileEntityDummy))
			return null;
		TileEntityDummy real = (TileEntityDummy) tileEntity;
		InteractiveEntity entity = real.getEntity();
		if (hasInv)
		{
			container.load(player.inventory);
			Inventory.Meta invMeta = metaData.getMeta(Inventory.class);
			Inventory.Data invData = entity.get(Inventory.class);
			List<Slot> slots = Lists.newArrayList();
			for (SlotInfo info : invMeta.getSlots())
				slots.add(new Slot(invData.getInventory(info), info.id(), info.x(), info.y()));
			for (SpaceInfo space : invMeta.getSpaces())
			{
				IInventory inv = invData.getInventory(space);
				for (int i = 0; i < space.x(); i++)
					for (int j = 0; j < space.y(); j++)
					{
						SlotInfo temp = space.get(i, j);
						slots.add(new Slot(inv, temp.id(), temp.x(), temp.y()));
					}
			}
			container.loadSlots(slots);
			if (hasProc)
			{
				ProcessPipeline.Data data = entity.get(ProcessPipeline.class);
				container.load(data.getVars());

				List<Pair<SlotInfo, Var<ItemStack>>> stack = data.getStack();
				for (Pair<SlotInfo, Var<ItemStack>> pair : stack)
					invData.assign(pair.getLeft(), pair.getRight());
			}
		}
		if (hasProc)
		{
			ProcessPipeline.Data data = entity.get(ProcessPipeline.class);
			container.load(data.getVars());
		}
		return container;
	}

	@Override
	public GuiScreen getGuiContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiContainerCommon(this.getContainer(player, world, x, y, z), this.provider);
	}
}
