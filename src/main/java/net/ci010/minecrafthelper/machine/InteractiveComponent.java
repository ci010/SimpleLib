package net.ci010.minecrafthelper.machine;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.gui.Drawable;
import net.ci010.minecrafthelper.gui.GuiComponent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class InteractiveComponent implements ContainerProvider
{
	protected String name;
	protected Class<? extends Process>[] clz;
	protected MachineBuilder.ProcessInfo[] info;
	@SideOnly(Side.CLIENT)
	protected Drawable[] front, back;
	protected int id, numOfProcess, numOfSync, numOfInt, numOfStack;

	public InteractiveComponent(InteractiveComponentBuilder info)
	{
		this.id = GuiHandler.addContainerProvider(this);
		this.name = info.name;

		this.numOfProcess = info.processInfoMap.size();
		this.clz = new Class[numOfProcess];
		this.info = new InteractiveComponentBuilder.ProcessInfo[numOfProcess];

		int idx = -1;
		numOfSync = 0;
		for (Map.Entry<Class<? extends Process>, InteractiveComponentBuilder.ProcessInfo> entry : info.processInfoMap.entrySet())
		{
			InteractiveComponentBuilder.ProcessInfo temp = entry.getValue();
			numOfSync += temp.sync.size();
			numOfInt += temp.integers.size();
			numOfStack += temp.stacks.size();
			numOfSync += temp.sync.size();
			this.clz[++idx] = entry.getKey();
			this.info[idx] = temp;
		}

		if (HelperMod.proxy.isClient())
		{
			List<Drawable> front = Lists.newArrayList(), back = Lists.newArrayList();
			for (GuiComponent component : info.gui)
				if (component.type() == GuiComponent.Type.back)
					back.add(component);
				else
					front.add(component);
			this.front = (Drawable[]) front.toArray();
			this.back = (Drawable[]) back.toArray();
		}
	}

	public String getName()
	{
		return this.name;
	}

	public void interactWith(EntityPlayer player, BlockPos pos)
	{
		player.openGui(HelperMod.instance, this.id, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGuiContainer(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiContainerWrap(getContainer(ID, player, world, x, y, z))
		{
			@Override
			protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
			{
				for (Drawable guiComponent : front)
					guiComponent.draw();
			}

			@Override
			protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
			{
				for (Drawable guiComponent : back)
					guiComponent.draw();
			}
		};
	}
}
