package net.ci010.minecrafthelper.machine.simple;

import net.ci010.minecrafthelper.interactive.ContainerWrap;
import net.ci010.minecrafthelper.interactive.InteractiveEntity;
import net.ci010.minecrafthelper.interactive.InteractiveInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class MachineMetaSimple extends InteractiveMeta
{
	protected BlockSimpleMachine block;

	protected MachineMetaSimple(InteractiveInfo info)
	{
		super(info);
	}

	@Override
	public Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerWrap(player.inventory);
	}

	@Override
	public InteractiveEntity createEntity()
	{
		return super.createEntity();
	}
}
