package net.ci010.minecrafthelper.wrap;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.test.VarInteger;
import net.ci010.minecrafthelper.test.VarSync;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author ci010
 */
public abstract class ContainerWrap extends Container implements VarSync.Listener
{
	private VarInteger[] varIntegers;

	public VarSync getVar(int idx)
	{
		return varIntegers[idx];
	}

	public ContainerWrap(InventoryPlayer player, VarInteger[] integers)
	{
		this.varIntegers = integers;
		int index;
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(player, index, 8 + index * 18, 142));
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(player, offset + index * 9 + 9, 8 + offset * 18, 84 + index * 18));
		index = -1;
//		for (VarItemHolder holder : holders)
//			this.addSlotToContainer(new Slot(tileEntityWrap, ++index, 0, 0));
		//TODO handle the position
	}

	@Override
	public List<EntityPlayerMP> getListeners()
	{
		//TODO maybe cache?
		List<EntityPlayerMP> players = Lists.newArrayList();
		for (Object o : this.crafters)
			if (o instanceof EntityPlayerMP)
				players.add((EntityPlayerMP) o);
		return players;
	}

	@Override
	public void onCraftGuiOpened(ICrafting iCrafting)
	{
		for (int i = 0; i < varIntegers.length; ++i)
			iCrafting.sendProgressBarUpdate(this, i, varIntegers[i].getData());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value)
	{
		varIntegers[id].setData(value);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		//TODO try to use generic to handle all varSync, try to recreate items tck
//		for (VarInteger i : varIntegers)
//			if (i.shouldUpdate())
//				i.update();
		int num = -1;
		for (VarInteger i : varIntegers)
			if (i.isDirty() && ++num != -1)
				for (EntityPlayerMP player : getListeners())
					player.sendProgressBarUpdate(this, num, i.getData());
	}
}
